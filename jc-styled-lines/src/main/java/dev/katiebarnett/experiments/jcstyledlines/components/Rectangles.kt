package dev.katiebarnett.experiments.jcstyledlines.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme

private val LINE_WIDTH = 8.dp
private val RECT_RADIUS = 40f

@Composable
fun MultiDashedRect(color: Color, modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    with(density) {
        val dashOnInterval1 = (LINE_WIDTH * 4).toPx()
        val dashOffInterval1 = (LINE_WIDTH * 2).toPx()
        val dashOnInterval2 = (LINE_WIDTH / 4).toPx()
        val dashOffInterval2 = (LINE_WIDTH * 2).toPx()

        val pathEffect =
            PathEffect.dashPathEffect(
                floatArrayOf(dashOnInterval1, dashOffInterval1, dashOnInterval2, dashOffInterval2),
                0f
            )
        Canvas(modifier) {
            drawRoundRect(
                color = color,
                cornerRadius = CornerRadius(RECT_RADIUS),
                style = Stroke(
                    width = LINE_WIDTH.toPx(),
                    cap = StrokeCap.Round,
                    pathEffect = pathEffect,
                ),
            )
        }
    }
}

@Composable
fun HeartRectTranslate(color: Color, modifier: Modifier = Modifier) {
    val shapeRadius = LINE_WIDTH / 2f
    val dotSpacing = shapeRadius * 4
    val density = LocalDensity.current

    // https://blog.canopas.com/how-to-apply-stroke-effects-to-text-in-jetpack-compose-b1c02c9907bd
    val heartPath = remember {
        with(density) {
            Path().apply {
                val width = (shapeRadius * 2).toPx()
                val height = (shapeRadius * 2).toPx()
                moveTo(width / 2, height / 4)
                cubicTo(width / 4, 0f, 0f, height / 3, width / 4, height / 2)
                lineTo(width / 2, height * 3 / 4)
                lineTo(width * 3 / 4, height / 2)
                cubicTo(width, height / 3, width * 3 / 4, 0f, width / 2, height / 4)
            }
        }
    }
    Canvas(modifier) {
        val pathEffect = PathEffect.stampedPathEffect(
            shape = heartPath,
            advance = dotSpacing.toPx(),
            phase = 0f,
            style = StampedPathEffectStyle.Translate
        )
        drawRoundRect(
            color = color,
            cornerRadius = CornerRadius(RECT_RADIUS),
            style = Stroke(
                width = LINE_WIDTH.toPx(),
                pathEffect = pathEffect,
            ),
        )
    }
}

@Composable
fun HeartRectRotate(color: Color, modifier: Modifier = Modifier) {
    val shapeRadius = LINE_WIDTH / 2f
    val dotSpacing = shapeRadius * 4
    val density = LocalDensity.current

    // https://blog.canopas.com/how-to-apply-stroke-effects-to-text-in-jetpack-compose-b1c02c9907bd
    val heartPath = remember {
        with(density) {
            Path().apply {
                val width = (shapeRadius * 2).toPx()
                val height = (shapeRadius * 2).toPx()
                moveTo(width / 2, height / 4)
                cubicTo(width / 4, 0f, 0f, height / 3, width / 4, height / 2)
                lineTo(width / 2, height * 3 / 4)
                lineTo(width * 3 / 4, height / 2)
                cubicTo(width, height / 3, width * 3 / 4, 0f, width / 2, height / 4)
            }
        }
    }
    Canvas(modifier) {
        val pathEffect = PathEffect.stampedPathEffect(
            shape = heartPath,
            advance = dotSpacing.toPx(),
            phase = 0f,
            style = StampedPathEffectStyle.Rotate
        )
        drawRoundRect(
            color = color,
            cornerRadius = CornerRadius(RECT_RADIUS),
            style = Stroke(
                width = LINE_WIDTH.toPx(),
                pathEffect = pathEffect,
            ),
        )
    }
}

@Composable
fun HeartRectMorph(color: Color, modifier: Modifier = Modifier) {
    val shapeRadius = LINE_WIDTH / 2f
    val dotSpacing = shapeRadius * 4
    val density = LocalDensity.current

    // https://blog.canopas.com/how-to-apply-stroke-effects-to-text-in-jetpack-compose-b1c02c9907bd
    val heartPath = remember {
        with(density) {
            Path().apply {
                val width = (shapeRadius * 2).toPx()
                val height = (shapeRadius * 2).toPx()
                moveTo(width / 2, height / 4)
                cubicTo(width / 4, 0f, 0f, height / 3, width / 4, height / 2)
                lineTo(width / 2, height * 3 / 4)
                lineTo(width * 3 / 4, height / 2)
                cubicTo(width, height / 3, width * 3 / 4, 0f, width / 2, height / 4)
            }
        }
    }
    Canvas(modifier) {
        val pathEffect = PathEffect.stampedPathEffect(
            shape = heartPath,
            advance = dotSpacing.toPx(),
            phase = 0f,
            style = StampedPathEffectStyle.Morph
        )
        drawRoundRect(
            color = color,
            cornerRadius = CornerRadius(RECT_RADIUS),
            style = Stroke(
                width = LINE_WIDTH.toPx(),
                pathEffect = pathEffect,
            ),
        )
    }
}

@Composable
fun DoubleSolidDashedRect(color: Color, modifier: Modifier = Modifier) {
    val dotRadius = 8.dp
    val dotSpacing = dotRadius * 4

    Canvas(modifier) {
        val circle = Path()
        circle.addOval(Rect(center = Offset.Zero, radius = dotRadius.toPx()))
        val solidPathEffect = PathEffect.dashPathEffect(
            floatArrayOf(10f, 20f),
            25f
        )
        val dotPathEffect = PathEffect.stampedPathEffect(
            shape = circle,
            advance = dotSpacing.toPx(),
            phase = 0f,
            style = StampedPathEffectStyle.Translate
        )

        val chainedPathEffect = PathEffect.chainPathEffect(inner = solidPathEffect, outer = dotPathEffect)
        drawRoundRect(
            color = color,
            cornerRadius = CornerRadius(RECT_RADIUS),
            style = Stroke(
                width = LINE_WIDTH.toPx(),
                pathEffect = chainedPathEffect,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RectanglesPreview() {
    ExperimentsTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(LINE_WIDTH * 2),
            modifier = Modifier.padding(16.dp).fillMaxWidth().height(100.dp)
        ) {
            MultiDashedRect(Color.LightGray, Modifier.fillMaxHeight().weight(1f))
            HeartRectTranslate(Color.Magenta, Modifier.fillMaxHeight().weight(1f))
            HeartRectRotate(Color.Magenta, Modifier.fillMaxHeight().weight(1f))
            HeartRectMorph(Color.Magenta, Modifier.fillMaxHeight().weight(1f))
            DoubleSolidDashedRect(Color.Magenta, Modifier.fillMaxHeight().weight(1f))
        }
    }
}
