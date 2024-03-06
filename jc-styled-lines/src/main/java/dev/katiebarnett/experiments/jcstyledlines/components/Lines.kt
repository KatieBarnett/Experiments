package dev.katiebarnett.experiments.jcstyledlines.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme

private val LINE_WIDTH = 16.dp

@Composable
fun SolidLine(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = LINE_WIDTH.toPx()
        )
    }
}

@Composable
fun SolidLineRoundedEnds(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            cap = StrokeCap.Round,
            strokeWidth = LINE_WIDTH.toPx()
        )
    }
}

@Composable
fun DashedLine(color: Color, modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    with(density) {
        val dashOnInterval = (LINE_WIDTH * 4).toPx()
        val dashOffInterval = (LINE_WIDTH * 4).toPx()

        val pathEffect =
            PathEffect.dashPathEffect(
                floatArrayOf(dashOnInterval, dashOffInterval),
                0f
            )
        Canvas(modifier) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = LINE_WIDTH.toPx(),
                cap = StrokeCap.Round,
                pathEffect = pathEffect,
            )
        }
    }
}

@Composable
fun MultiDashedLine(color: Color, modifier: Modifier = Modifier) {
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
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = LINE_WIDTH.toPx(),
                cap = StrokeCap.Round,
                pathEffect = pathEffect,
            )
        }
    }
}

@Composable
fun DottedLine(color: Color, modifier: Modifier = Modifier) {
    val dotRadius = LINE_WIDTH / 2
    val dotSpacing = dotRadius * 2

    Canvas(modifier) {
        val circle = Path()
        circle.addOval(Rect(center = Offset.Zero, radius = dotRadius.toPx()))
        val pathEffect = PathEffect.stampedPathEffect(
            shape = circle,
            advance = dotSpacing.toPx(),
            phase = 0f,
            style = StampedPathEffectStyle.Translate
        )
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect,
            cap = StrokeCap.Round,
            strokeWidth = dotRadius.toPx()
        )
    }
}

@Composable
fun DottedSeparatedLine(color: Color, modifier: Modifier = Modifier) {
    val dotRadius = LINE_WIDTH / 2
    val dotSpacing = dotRadius * 4

    Canvas(modifier) {
        val circle = Path()
        circle.addOval(Rect(center = Offset.Zero, radius = dotRadius.toPx()))
        val pathEffect = PathEffect.stampedPathEffect(
            shape = circle,
            advance = dotSpacing.toPx(),
            phase = 0f,
            style = StampedPathEffectStyle.Translate
        )
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect,
            cap = StrokeCap.Round,
            strokeWidth = dotRadius.toPx()
        )
    }
}

@Composable
fun HeartLine(color: Color, modifier: Modifier = Modifier) {
    val shapeRadius = LINE_WIDTH / 2
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
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect,
            strokeWidth = shapeRadius.toPx()
        )
    }
}

@Composable
fun DoubleSolidDashedLine(color: Color, modifier: Modifier = Modifier) {
    val dotRadius = LINE_WIDTH / 2
    val dotSpacing = dotRadius * 4

    val density = LocalDensity.current
    with(density) {
        val dashOnInterval1 = (LINE_WIDTH * 4).toPx()
        val dashOffInterval1 = (LINE_WIDTH * 2).toPx()
        val dashOnInterval2 = (LINE_WIDTH / 4).toPx()
        val dashOffInterval2 = (LINE_WIDTH * 2).toPx()

        val circle = Path()
        circle.addOval(Rect(center = Offset.Zero, radius = dotRadius.toPx()))
        val dotPathEffect = PathEffect.stampedPathEffect(
            shape = circle,
            advance = dotSpacing.toPx(),
            phase = 0f,
            style = StampedPathEffectStyle.Translate
        )

        val solidPathEffect = PathEffect.cornerPathEffect(10f)

        val multiDashPathEffect =
            PathEffect.dashPathEffect(
                floatArrayOf(dashOnInterval1, dashOffInterval1),
                0f
            )
        Canvas(modifier) {

            val chainedPathEffect = PathEffect.chainPathEffect(inner = multiDashPathEffect, outer = solidPathEffect)
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                pathEffect = chainedPathEffect,
                strokeWidth = LINE_WIDTH.toPx()
            )
        }
    }
}

@Composable
fun WavyLine(color: Color, modifier: Modifier = Modifier) {
val amplitude = 100f
    Canvas(modifier = modifier.fillMaxSize()) {
        val path = Path()
        val centerY = size.height / 2f
        val centerX = size.width / 2f

        // Move to starting point (one-quarter width from left, center of canvas)
        path.moveTo(centerX / 4f, centerY)

        // Loop through half width of canvas, calculating y position for sine wave
        for (x in 0f..centerX) {
            val y = centerY + amplitude * Math.sin(2 * Math.PI * x / centerX)
            path.lineTo(x, y)
        }

        // Since it's a single cycle, mirror the drawn path to complete the wave
        path.lineTo(centerX, centerY) // Connect endpoint to center for mirroring
        for (x in centerX downTo 0f) {
            val mirroredY = centerY - amplitude * Math.sin(2 * Math.PI * (centerX - x) / centerX)
            path.lineTo(x, mirroredY)
        }

        // Draw the completed path with blue color, stroke width, and round caps
        drawPath(
            path = path,
            color = Color.Blue,
            strokeWidth = 2.dp.toPx(),
            strokeCap = StrokeCap.Round,
            strokeJoin = StrokeJoin.Round
        )
    }

//    val density = LocalDensity.current
//    val wavePath = remember {
//        with(density) {
//
//            val height = LINE_WIDTH.toPx()
//            val width = (LINE_WIDTH*4).toPx()
//
//            val wavePath = Path()
//            val centerY = height / 2f
//            val centerX = width / 2f
//            val amplitude = 100f
//
//            // Move to starting point (one-quarter width from left, center of canvas)
//            wavePath.moveTo(centerX / 4f, centerY)
//
//            // Loop through half width of canvas, calculating y position for sine wave
//            for (x in 0f..centerX) {
//                val y = centerY + amplitude * Math.sin(2 * Math.PI * x / centerX)
//                wavePath.lineTo(x, y)
//            }
//
//            // Since it's a single cycle, mirror the drawn path to complete the wave
//            wavePath.lineTo(centerX, centerY) // Connect endpoint to center for mirroring
//            for (x in centerX downTo 0f) {
//                val mirroredY = centerY - amplitude * Math.sin(2 * Math.PI * (centerX - x) / centerX)
//                wavePath.lineTo(x, mirroredY)
//            }



//
//            Path().apply {
//
//
//
//                val path = Path()
//                val centerY = (LINE_WIDTH / 2f).toPx()
//
//                // Move to starting point (left edge, center of canvas)
//                path.moveTo(0f, centerY)
//
//                // Loop through width of canvas, calculating y position for sine wave
//                for (x in 0f..size.width) {
//                    val y = centerY + amplitude * Math.sin(x * Math.PI * frequency / size.width)
//                    path.lineTo(x, y)
//                }
//
//
////                val width = (LINE_WIDTH * 4).toPx()
////                val height = (LINE_WIDTH).toPx()
////                moveTo(width / 2, height / 2)
////                cubicTo(width / 4, 0f, 0f, height / 3, width / 4, height / 2)
////                lineTo(width / 2, height * 3 / 4)
////                lineTo(width * 3 / 4, height / 2)
////                cubicTo(width, height / 3, width * 3 / 4, 0f, width / 2, height / 4)
//            }
//        }
//    }
//    Canvas(modifier) {
//        val pathEffect = PathEffect.stampedPathEffect(
//            shape = wavePath,
//            advance = 0f,
//            phase = 0f,
//            style = StampedPathEffectStyle.Translate
//        )
//        drawLine(
//            color = color,
//            start = Offset(0f, 0f),
//            end = Offset(size.width, 0f),
//            pathEffect = pathEffect,
//            strokeWidth = LINE_WIDTH.toPx()
//        )
//    }
}

@Composable
fun GradientLine(color: Color, modifier: Modifier = Modifier) {
}

@Preview(showBackground = true)
@Composable
fun LinePreview() {
    ExperimentsTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(LINE_WIDTH * 2),
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            SolidLine(Color.Black, Modifier.fillMaxWidth())
            SolidLineRoundedEnds(Color.Red, Modifier.fillMaxWidth())
            DashedLine(Color.Yellow, Modifier.fillMaxWidth())
            MultiDashedLine(Color.LightGray, Modifier.fillMaxWidth())
            DottedLine(Color.Green, Modifier.fillMaxWidth())
            DottedSeparatedLine(Color.Cyan, Modifier.fillMaxWidth())
            HeartLine(Color.Magenta, Modifier.fillMaxWidth())
            DoubleSolidDashedLine(Color.Magenta, Modifier.fillMaxWidth())
            WavyLine(Color.Blue, Modifier.fillMaxWidth())
            GradientLine(Color.Blue, Modifier.fillMaxWidth())
        }
    }
}
