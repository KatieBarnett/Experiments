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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme

private val LINE_WIDTH = 8.dp
private val RECT_RADIUS = 40f

// https://blog.canopas.com/how-to-apply-stroke-effects-to-text-in-jetpack-compose-b1c02c9907bd
fun getHeartPath(density: Density): Path {
    val shapeRadius = LINE_WIDTH / 2
    with(density) {
        return Path().apply {
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

fun getZigZagPath(density: Density): Path {
    val shapeWidth = LINE_WIDTH
    with(density) {
        return Path().apply {
            val zigZagWidth = shapeWidth.toPx()
            val zigZagHeight = shapeWidth.toPx()
            val zigZagLineWidth = (1.dp).toPx()
            val shapeVerticalOffset = (zigZagHeight / 2) / 2
            val shapeHorizontalOffset = (zigZagHeight / 2) / 2
            moveTo(0f, 0f)
            lineTo(zigZagWidth / 2, zigZagHeight / 2)
            lineTo(zigZagWidth, 0f)
            lineTo(zigZagWidth, 0f + zigZagLineWidth)
            lineTo(zigZagWidth / 2, zigZagHeight / 2 + zigZagLineWidth)
            lineTo(0f, 0f + zigZagLineWidth)
            translate(Offset(-shapeHorizontalOffset, -shapeVerticalOffset))
        }
    }
}

fun getCubicPath(size: Size): Path {
    return Path().apply {
        moveTo(0f, 0f)
        cubicTo(
            x1 = size.width / 4,
            y1 = size.height,
            x2 = size.width / 4 * 3,
            y2 = size.height / 4,
            x3 = size.width,
            y3 = size.height
        )
    }
}

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
fun MultiDashedCircle(color: Color, modifier: Modifier = Modifier) {
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
            drawCircle(
                color = color,
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
fun MultiDashedPath(color: Color, modifier: Modifier = Modifier) {
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
            val path = getCubicPath(size)
            drawPath(
                color = Color.LightGray,
                path = path,
                style = Stroke(
                    width = LINE_WIDTH.toPx(),
                    cap = StrokeCap.Round,
                    pathEffect = pathEffect
                )
            )
        }
    }
}

@Composable
fun HeartRectTranslate(color: Color, modifier: Modifier = Modifier) {
    val shapeRadius = LINE_WIDTH / 2f
    val dotSpacing = shapeRadius * 4
    val density = LocalDensity.current
    val heartPath = remember { getHeartPath(density) }
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
    val heartPath = remember { getHeartPath(density) }
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
    val heartPath = remember { getHeartPath(density) }
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
fun ZigZagRectTranslate(color: Color, modifier: Modifier = Modifier) {
    val shapeWidth = LINE_WIDTH
    val density = LocalDensity.current
    val zigZagPath = remember { getZigZagPath(density) }
    Canvas(modifier) {
        val pathEffect = PathEffect.stampedPathEffect(
            shape = zigZagPath,
            advance = shapeWidth.toPx(),
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
fun ZigZagRectRotate(color: Color, modifier: Modifier = Modifier) {
    val shapeWidth = LINE_WIDTH
    val density = LocalDensity.current
    val zigZagPath = remember { getZigZagPath(density) }
    Canvas(modifier) {
        val pathEffect = PathEffect.stampedPathEffect(
            shape = zigZagPath,
            advance = shapeWidth.toPx(),
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
fun ZigZagRectMorph(color: Color, modifier: Modifier = Modifier) {
    val shapeWidth = LINE_WIDTH
    val density = LocalDensity.current
    val zigZagPath = remember { getZigZagPath(density) }
    Canvas(modifier) {
        val pathEffect = PathEffect.stampedPathEffect(
            shape = zigZagPath,
            advance = shapeWidth.toPx(),
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

// ????
// @Composable
// fun DoubleSolidDashedRect(color: Color, modifier: Modifier = Modifier) {
//    val dotRadius = 8.dp
//    val dotSpacing = dotRadius * 4
//
//    Canvas(modifier) {
//        val circle = Path()
//        circle.addOval(Rect(center = Offset.Zero, radius = dotRadius.toPx()))
//        val solidPathEffect = PathEffect.dashPathEffect(
//            floatArrayOf(10f, 20f),
//            25f
//        )
//        val dotPathEffect = PathEffect.stampedPathEffect(
//            shape = circle,
//            advance = dotSpacing.toPx(),
//            phase = 0f,
//            style = StampedPathEffectStyle.Translate
//        )
//
//        val chainedPathEffect = PathEffect.chainPathEffect(inner = solidPathEffect, outer = dotPathEffect)
//        drawRoundRect(
//            color = color,
//            cornerRadius = CornerRadius(RECT_RADIUS),
//            style = Stroke(
//                width = LINE_WIDTH.toPx(),
//                pathEffect = chainedPathEffect,
//            ),
//        )
//    }
// }

@Preview(showBackground = true)
@Composable
fun ShapesPreview() {
    ExperimentsTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(LINE_WIDTH * 2),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(100.dp)
        ) {
            MultiDashedRect(Color.LightGray, Modifier.fillMaxHeight().weight(1f))
            MultiDashedCircle(Color.LightGray, Modifier.fillMaxHeight().weight(1f))
            MultiDashedPath(Color.LightGray, Modifier.fillMaxHeight().weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeartShapesPreview() {
    ExperimentsTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(LINE_WIDTH * 2),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(100.dp)
        ) {
            HeartRectTranslate(Color.Magenta, Modifier.fillMaxHeight().weight(1f))
            HeartRectRotate(Color.Magenta, Modifier.fillMaxHeight().weight(1f))
            HeartRectMorph(Color.Magenta, Modifier.fillMaxHeight().weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WaveShapesPreview() {
    ExperimentsTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(LINE_WIDTH * 2),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(100.dp)
        ) {
            ZigZagRectTranslate(Color.Blue, Modifier.fillMaxHeight().weight(1f))
            ZigZagRectRotate(Color.Blue, Modifier.fillMaxHeight().weight(1f))
            ZigZagRectMorph(Color.Blue, Modifier.fillMaxHeight().weight(1f))
        }
    }
}
