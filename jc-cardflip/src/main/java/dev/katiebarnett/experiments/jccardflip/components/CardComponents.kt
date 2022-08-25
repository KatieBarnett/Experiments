package dev.katiebarnett.experiments.jccardflip.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.katiebarnett.experiments.core.theme.Dimen
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme
import dev.katiebarnett.experiments.jccardflip.R
import dev.katiebarnett.experiments.jccardflip.models.CardBack
import dev.katiebarnett.experiments.jccardflip.models.CardFace
import dev.katiebarnett.experiments.jccardflip.models.CardFront

@Composable
fun CardFaceDisplay(
    cardFace: CardFace?,
    modifier: Modifier = Modifier
) {
    if (cardFace != null) {
        Box(contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(Dimen.Card.radius))
                .border(
                    Dimen.Card.border,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(Dimen.Card.radius)
                )
                .background(MaterialTheme.colorScheme.surface)
        ) {
            when (cardFace) {
                is CardFront -> CardFrontContent(cardFace)
                CardBack -> CardBackContent()
            }
        }
    } else {
        CardPlaceholderDisplay(modifier)
    }
}


@Composable
private fun CardFrontContent(
    cardFace: CardFront,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()
        .padding(paddingValues = PaddingValues(Dimen.spacing, Dimen.spacingHalf))) {
        Image(
            painter = painterResource(cardFace.drawableRes ?: 0),
            colorFilter = ColorFilter.tint(cardFace.cardColor ?: Black),
            contentDescription = null,
            modifier = modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(0.6f)
                .align(Alignment.Center)
        )
        CardValue(cardFace = cardFace, 
            modifier = Modifier
                .fillMaxWidth(0.15f)
                .align(Alignment.TopStart)
        )
        CardValue(cardFace = cardFace,
            modifier = Modifier
                .fillMaxWidth(0.15f)
                .rotate(180f)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
private fun CardValue(
    cardFace: CardFront,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = cardFace.valueRes),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = cardFace.cardColor
        )
        Image(
            painter = painterResource(cardFace.drawableRes),
            colorFilter = ColorFilter.tint(cardFace.cardColor),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
private fun CardBackContent(
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(Dimen.spacing)
    ) {
        Text(text = "Let's get flippin'",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary)
    }
}

@Composable
fun CardPlaceholderDisplay(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(Dimen.Card.radius))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(text = stringResource(id = R.string.empty_stack))
    }
}

@Preview(showBackground = true)
@Composable
fun CardValuePreview() {
    ExperimentsTheme {
        CardValue(CardFront(R.drawable.heart, Red, R.string.card_value_10), modifier = Modifier
            .width(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CardFaceDisplayPreview() {
    ExperimentsTheme {
        Row(Modifier.height(200.dp)) {
            CardFaceDisplay(
                CardFront(R.drawable.club, Black, R.string.card_value_J), modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            CardFaceDisplay(
                CardFront(R.drawable.heart, Red, R.string.card_value_10), modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            CardFaceDisplay(
                CardBack, modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardDisplayPlaceholderPreview() {
    ExperimentsTheme {
        CardFaceDisplay(null, modifier = Modifier
            .height(400.dp)
            .width(300.dp))
    }
}