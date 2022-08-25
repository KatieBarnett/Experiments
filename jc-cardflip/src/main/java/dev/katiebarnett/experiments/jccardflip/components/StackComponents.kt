package dev.katiebarnett.experiments.jccardflip.components

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.katiebarnett.experiments.core.theme.Black
import dev.katiebarnett.experiments.core.theme.Dimen
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme
import dev.katiebarnett.experiments.core.theme.Red
import dev.katiebarnett.experiments.jccardflip.R
import dev.katiebarnett.experiments.jccardflip.StackViewModel
import dev.katiebarnett.experiments.jccardflip.models.Card
import dev.katiebarnett.experiments.jccardflip.models.CardBack
import dev.katiebarnett.experiments.jccardflip.models.CardFront

@Composable
fun Stack(deck: List<Card>,
          position: Int,
          modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<StackViewModel>()
    viewModel.setDeck(deck)
    viewModel.setPosition(position)
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        StackLayout(
            flipCard = viewModel.flipCard,
            leftStack = { modifier ->
                CardFaceDisplay(cardFace = viewModel.leftStackTop?.back, modifier)
            }, rightStack = { modifier ->
                CardFaceDisplay(cardFace = viewModel.rightStackTop?.front, modifier)
            }, 
            transitionTrigger = position, 
            modifier = modifier
        )
    }
}

@Composable
fun NonAnimatedStack(deck: List<Card>,
                     position: Int,
                     modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<StackViewModel>()
    viewModel.setDeck(deck)
    viewModel.setPosition(position)
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        NonAnimatedStackLayout(
            flipCard = viewModel.flipCard,
            leftStack = { modifier ->
                CardFaceDisplay(cardFace = viewModel.leftStackTop?.back, modifier)
            }, rightStack = { modifier ->
                CardFaceDisplay(cardFace = viewModel.rightStackTop?.front, modifier)
            },
            modifier = modifier)
    }
}

@Composable
fun StackLayout(
    flipCard: Card?,
    leftStack: @Composable (modifier: Modifier) -> Unit,
    rightStack: @Composable (modifier: Modifier) -> Unit,
    transitionTrigger: Int = 0,
    modifier: Modifier = Modifier
) {
    var offset by remember(transitionTrigger) { mutableStateOf(0f) }
    var flipRotation by remember(transitionTrigger) { mutableStateOf(0f) }
    val animationSpec = tween<Float>(1000, easing = CubicBezierEasing(0.4f, 0.0f, 0.8f, 0.8f))
    val animationSpecFlip = tween<Float>(1000, easing = CubicBezierEasing(0.4f, 0.0f, 0.8f, 0.8f))

    LaunchedEffect(key1 = transitionTrigger) {
        // Translate card to right stack
        animate(initialValue = 0f, targetValue = 1f, animationSpec = animationSpec) { value: Float, _: Float ->
            offset = value
        }
        // Do the flip
        animate(initialValue = 0f, targetValue = 180f, animationSpec = animationSpecFlip) { value: Float, _: Float ->
            flipRotation = value
        }
    }

    Layout(
        modifier = modifier.fillMaxSize(),
        content = {
            leftStack(modifier = Modifier.layoutId("LeftStack"))
            rightStack(modifier = Modifier.layoutId("RightStack"))
            flipCard?.let {
                val modifier = Modifier
                    .layoutId("FlipCard")
                    .graphicsLayer {
                        rotationY = flipRotation
                        cameraDistance = 8 * density
                    }
                if (flipRotation < 90f) {
                    CardFaceDisplay(flipCard.back, modifier)
                } else {
                    // Rotate the action card back again so it does not appear reversed
                    CardFaceDisplay(flipCard.front,
                        modifier = modifier.graphicsLayer {
                            rotationY = 180f
                        }
                    )
                }
            }
        }) { measurables, constraints ->

        val flipCardPlaceable =
            measurables.firstOrNull { it.layoutId == "FlipCard" }
        val leftStackPlaceable =
            measurables.firstOrNull { it.layoutId == "LeftStack" }
        val rightStackPlaceable =
            measurables.firstOrNull { it.layoutId == "RightStack"} 

        layout(constraints.maxWidth, constraints.maxHeight) {
            val cardSpacing = Dimen.Card.spacing.toPx()
            val cardWidth = ((constraints.maxWidth - cardSpacing) / 2).toInt()
            val cardConstraints = constraints.copy(
                minWidth = minOf(constraints.minWidth, cardWidth),
                maxWidth = cardWidth
            )

            val leftStackX = 0
            val rightStackX = leftStackX + cardSpacing + cardWidth
            val flipCardX = rightStackX * offset

            leftStackPlaceable?.measure(cardConstraints)?.place(leftStackX, 0)
            rightStackPlaceable?.measure(cardConstraints)?.place(rightStackX.toInt(), 0)
            flipCardPlaceable?.measure(cardConstraints)?.place(flipCardX.toInt(), 0)
        }
    }
}

@Composable
fun StackTranslateLayout(
    flipCard: Card?,
    leftStack: @Composable (modifier: Modifier) -> Unit,
    rightStack: @Composable (modifier: Modifier) -> Unit,
    transitionTrigger: Int = 0,
    modifier: Modifier = Modifier
) {
    var offset by remember() { mutableStateOf(0f) }
    val animationSpec = tween<Float>(1000, easing = CubicBezierEasing(0.4f, 0.0f, 0.8f, 0.8f))
    
    LaunchedEffect(key1 = transitionTrigger) {
        // Translate card to right stack
        animate(initialValue = 0f, targetValue = 1f, animationSpec = animationSpec) { value: Float, _: Float ->
            offset = value
        }
    }

    Layout(
        modifier = modifier.fillMaxSize(),
        content = {
            leftStack(modifier = Modifier.layoutId("LeftStack"))
            rightStack(modifier = Modifier.layoutId("RightStack"))
            flipCard?.let {
                CardFaceDisplay(flipCard.back, Modifier.layoutId("FlipCard"))
            }
        }) { measurables, constraints ->

        val flipCardPlaceable =
            measurables.firstOrNull { it.layoutId == "FlipCard" }
        val leftStackPlaceable =
            measurables.firstOrNull { it.layoutId == "LeftStack" }
        val rightStackPlaceable =
            measurables.firstOrNull { it.layoutId == "RightStack"}

        layout(constraints.maxWidth, constraints.maxHeight) {
            val cardSpacing = Dimen.Card.spacing.toPx()
            val cardWidth = ((constraints.maxWidth - cardSpacing) / 2).toInt()
            val cardConstraints = constraints.copy(
                minWidth = minOf(constraints.minWidth, cardWidth),
                maxWidth = cardWidth
            )

            val leftStackX = 0
            val rightStackX = leftStackX + cardSpacing + cardWidth
            val flipCardX = rightStackX * offset

            leftStackPlaceable?.measure(cardConstraints)?.place(leftStackX, 0)
            rightStackPlaceable?.measure(cardConstraints)?.place(rightStackX.toInt(), 0)
            flipCardPlaceable?.measure(cardConstraints)?.place(flipCardX.toInt(), 0)
        }
    }
}

@Composable
fun NonAnimatedStackLayout(
    flipCard: Card?,
    leftStack: @Composable (modifier: Modifier) -> Unit,
    rightStack: @Composable (modifier: Modifier) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(horizontalArrangement = Arrangement.spacedBy(Dimen.Card.spacing), modifier = modifier) {
        Box(modifier = Modifier.weight(1f)) {
            leftStack
            CardFaceDisplay(flipCard?.back)
        }
        rightStack(modifier = Modifier.weight(1f))
    }
}

@Composable
fun NonAnimatedCustomStackLayout(
    flipCard: Card?,
    leftStack: @Composable (modifier: Modifier) -> Unit,
    rightStack: @Composable (modifier: Modifier) -> Unit,
    modifier: Modifier = Modifier
) {
    Layout(
        modifier = modifier.fillMaxSize(),
        content = {
            leftStack(modifier = Modifier.layoutId("LeftStack"))
            rightStack(modifier = Modifier.layoutId("RightStack"))
            flipCard?.let {
                CardFaceDisplay(flipCard.back, modifier = Modifier.layoutId("FlipCard"))
            }
        }) { measurables, constraints ->

        val flipCardPlaceable =
            measurables.firstOrNull { it.layoutId == "FlipCard" }
        val leftStackPlaceable =
            measurables.firstOrNull { it.layoutId == "LeftStack" }
        val rightStackPlaceable =
            measurables.firstOrNull { it.layoutId == "RightStack"}

        layout(constraints.maxWidth, constraints.maxHeight) {
            val cardSpacing = Dimen.Card.spacing.toPx()
            val cardWidth = ((constraints.maxWidth - cardSpacing) / 2).toInt()
            val cardConstraints = constraints.copy(
                minWidth = minOf(constraints.minWidth, cardWidth),
                maxWidth = cardWidth
            )

            val leftStackX = 0
            val rightStackX = leftStackX + cardSpacing + cardWidth
            val flipCardX = leftStackX

            leftStackPlaceable?.measure(cardConstraints)?.place(leftStackX, 0)
            rightStackPlaceable?.measure(cardConstraints)?.place(rightStackX.toInt(), 0)
            flipCardPlaceable?.measure(cardConstraints)?.place(flipCardX, 0)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NonAnimatedStackLayoutPreview() {
    ExperimentsTheme {
        NonAnimatedStackLayout(
            flipCard = Card(CardFront(R.drawable.heart, Red, R.string.card_value_4)),
            leftStack = { modifier ->
                CardFaceDisplay(cardFace = CardBack, modifier)
            }, rightStack = { modifier ->
                CardFaceDisplay(cardFace = CardFront(R.drawable.club, Black, R.string.card_value_10), modifier)
            },
            modifier = Modifier
                .height(400.dp)
                .padding(Dimen.spacingDouble))
    }
}

@Preview(showBackground = true)
@Composable
fun StackPreview() {
    ExperimentsTheme {
        StackLayout(
            flipCard = Card(CardFront(R.drawable.heart, Red, R.string.card_value_4)),
            leftStack = { modifier ->
                CardFaceDisplay(cardFace = CardBack, modifier)
            }, rightStack = { modifier ->
                CardFaceDisplay(cardFace = CardFront(R.drawable.club, Black, R.string.card_value_10), modifier)
            },
            transitionTrigger = 4,
            modifier = Modifier
                .height(400.dp)
                .padding(Dimen.spacingDouble))
    }
}

@Preview(showBackground = true)
@Composable
fun StackWithEmptyLeftPreview() {
    ExperimentsTheme {
        StackLayout(
            flipCard = null,
            leftStack = {  },
            rightStack = {
                CardFaceDisplay(cardFace = CardFront(R.drawable.club, Black, R.string.card_value_J))
            },
            modifier = Modifier
                .height(400.dp)
                .padding(Dimen.spacingDouble)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StackWithEmptyRightPreview() {
    ExperimentsTheme {
        StackLayout(
            flipCard = null,
            leftStack = {
                CardFaceDisplay(cardFace = CardBack)
            },
            rightStack = {  },
            modifier = Modifier
                .height(400.dp)
                .padding(Dimen.spacingDouble)
        )
    }
}