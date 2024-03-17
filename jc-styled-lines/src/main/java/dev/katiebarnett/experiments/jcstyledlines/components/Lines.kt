package dev.katiebarnett.experiments.jcstyledlines.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
fun SolidLineSquareEnds(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            cap = StrokeCap.Square,
            strokeWidth = LINE_WIDTH.toPx()
        )
    }
}

@Composable
fun DashedLine(color: Color, phaseDivider: Int, modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    with(density) {
        val dashOnInterval = (LINE_WIDTH * 4).toPx()
        val dashOffInterval = (LINE_WIDTH * 4).toPx()

        val pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(dashOnInterval, dashOffInterval),
            phase = if (phaseDivider == 0) 0f else dashOnInterval / phaseDivider
        )
        Canvas(modifier) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = LINE_WIDTH.toPx(),
                cap = StrokeCap.Butt,
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
                intervals = floatArrayOf(dashOnInterval1, dashOffInterval1, dashOnInterval2, dashOffInterval2),
                phase = 0f
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

// Heart shape:
// https://blog.canopas.com/how-to-apply-stroke-effects-to-text-in-jetpack-compose-b1c02c9907bd
@Composable
fun HeartLine(color: Color, modifier: Modifier = Modifier) {
    val shapeRadius = LINE_WIDTH / 2
    val dotSpacing = shapeRadius * 4
    val density = LocalDensity.current

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
fun ZigZagLineTriangles(color: Color, modifier: Modifier = Modifier) {
    val shapeWidth = LINE_WIDTH
    val density = LocalDensity.current
    val zigZagPath = remember {
        with(density) {
            Path().apply {
                val zigZagWidth = shapeWidth.toPx()
                val zigZagHeight = shapeWidth.toPx()
                moveTo(0f, 0f)
                lineTo(zigZagWidth / 2, zigZagHeight / 2)
                lineTo(zigZagWidth, 0f)
            }
        }
    }
    Canvas(modifier) {
        val pathEffect = PathEffect.stampedPathEffect(
            shape = zigZagPath,
            advance = shapeWidth.toPx(),
            phase = 0f,
            style = StampedPathEffectStyle.Translate
        )
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect,
            strokeWidth = shapeWidth.toPx()
        )
    }
}

@Composable
fun ZigZagLineBaseline(color: Color, modifier: Modifier = Modifier) {
    val shapeWidth = LINE_WIDTH
    val density = LocalDensity.current
    val zigZagPath = remember {
        with(density) {
            Path().apply {
                val zigZagWidth = shapeWidth.toPx()
                val zigZagHeight = shapeWidth.toPx()
                val zigZagLineWidth = (1.dp).toPx()
                moveTo(0f, 0f)
                lineTo(zigZagWidth / 2, zigZagHeight / 2)
                lineTo(zigZagWidth, 0f)
                lineTo(zigZagWidth, 0f + zigZagLineWidth)
                lineTo(zigZagWidth / 2, zigZagHeight / 2 + zigZagLineWidth)
                lineTo(0f, 0f + zigZagLineWidth)
            }
        }
    }
    Canvas(modifier) {
        val pathEffect = PathEffect.stampedPathEffect(
            shape = zigZagPath,
            advance = shapeWidth.toPx(),
            phase = 0f,
            style = StampedPathEffectStyle.Translate
        )
        drawLine(
            color = Color.Magenta,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = (1.dp).toPx()
        )
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect,
            strokeWidth = shapeWidth.toPx()
        )
    }
}

@Composable
fun ZigZagLine(color: Color, modifier: Modifier = Modifier) {
    val shapeWidth = LINE_WIDTH
    val density = LocalDensity.current
    val zigZagPath = remember {
        with(density) {
            Path().apply {
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
    Canvas(modifier) {
        val pathEffect = PathEffect.stampedPathEffect(
            shape = zigZagPath,
            advance = shapeWidth.toPx(),
            phase = 0f,
            style = StampedPathEffectStyle.Translate
        )
//        drawLine(
//            color = Color.Magenta,
//            start = Offset(0f, 0f),
//            end = Offset(size.width, 0f),
//            strokeWidth = (1.dp).toPx()
//        )
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect,
            strokeWidth = shapeWidth.toPx()
        )
    }
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
            SolidLineSquareEnds(Color.Blue, Modifier.fillMaxWidth())
            DashedLine(Color.Yellow, 0, Modifier.fillMaxWidth())
//            DashedLine(Color.Yellow, 2, Modifier.fillMaxWidth())
//            DashedLine(Color.Yellow, 1, Modifier.fillMaxWidth())
            MultiDashedLine(Color.LightGray, Modifier.fillMaxWidth())
            DottedLine(Color.Green, Modifier.fillMaxWidth())
            DottedSeparatedLine(Color.Cyan, Modifier.fillMaxWidth())
            HeartLine(Color.Magenta, Modifier.fillMaxWidth())

//            ZigZagLineTriangles(Color.Blue, Modifier.fillMaxWidth())
//            ZigZagLineBaseline(Color.Blue, Modifier.fillMaxWidth())
            ZigZagLine(Color.Blue, Modifier.fillMaxWidth())
        }
    }
}
