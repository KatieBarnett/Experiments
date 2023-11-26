package dev.katiebarnett.experiments.jcpreviews.components

import android.graphics.Bitmap
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme

@Composable
fun TileSideImage(
    image: Bitmap,
    contentDescription: String,
    background: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = background,
            contentColor = contentColor
        ),
        modifier = modifier.padding(8.dp)
    ) {
        Image(image.asImageBitmap(), contentDescription, Modifier.fillMaxSize())
    }
}

@Composable
fun TileSideDetails(
    name: String,
    description: String?,
    icon: ImageVector,
    background: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = background,
            contentColor = contentColor
        ),
        modifier = modifier.padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Image(
                icon,
                colorFilter = ColorFilter.tint(contentColor),
                contentDescription = description,
                modifier = Modifier
                    .fillMaxSize(0.3f)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(0.3f)
            ) {
                Text(
                    text = name,
                    color = contentColor,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayLarge
                )
            }
            description?.let {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(0.3f)
                ) {
                    Text(
                        text = description,
                        color = contentColor,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }
        }
    }
}

@Composable
fun Tile(
    sideFront: @Composable (Modifier) -> Unit,
    sideBack: @Composable (Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showingFront by remember { mutableStateOf<Boolean?>(null) }
    var flipRotation by remember { mutableFloatStateOf(0f) }
    val animationSpecFlip = tween<Float>(1000, easing = CubicBezierEasing(0.4f, 0.0f, 0.8f, 0.8f))

    showingFront?.let {
        LaunchedEffect(showingFront) {
            if (showingFront != false) {
                // Do the flip
                animate(
                    initialValue = 0f,
                    targetValue = 180f,
                    animationSpec = animationSpecFlip
                ) { value: Float, _: Float ->
                    flipRotation = value
                }
            } else {
                animate(
                    initialValue = 180f,
                    targetValue = 0f,
                    animationSpec = animationSpecFlip
                ) { value: Float, _: Float ->
                    flipRotation = value
                }
            }
        }
    }

    Box(
        modifier
            .aspectRatio(1f)
            .clickable { showingFront = !(showingFront ?: false) }) {
        val animatedModifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                rotationY = flipRotation
                cameraDistance = 8 * density
            }
        if (flipRotation < 90f) {
            sideFront.invoke(animatedModifier)
        } else {
            // Rotate the action card back again so it does not appear reversed
            sideBack.invoke(
                animatedModifier.graphicsLayer {
                    rotationY = 180f
                }
            )
        }
    }
}

@Preview
@Composable
fun TilePreview() {
    ExperimentsTheme {
        Tile(
            TileSideDetails(
                name = ""
            ),
            TileSideImage(),
        )
    }
}
