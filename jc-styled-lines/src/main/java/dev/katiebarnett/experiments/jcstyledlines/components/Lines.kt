package dev.katiebarnett.experiments.jcstyledlines.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap

package dev.katiebarnett.experiments.jcstyledlines.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap

if (style == ProgressLineStyle.DOT) {
    val circle = Path()
    circle.addOval(Rect(center = Offset.Zero, radius = dotWidth.toPx()))
    val pathEffect = PathEffect.stampedPathEffect(
        shape = circle,
        advance = dotSpacing.toPx(),
        phase = if (dotPhaseToStart) {
            PHASE_NONE
        } else {
            PHASE_SMALL_OFFSET
        },
        style = StampedPathEffectStyle.Translate
    )
    drawLine(
        color = lineColor,
        start = Offset(0f, 0f),
        end = Offset(0f, size.height),
        pathEffect = pathEffect,
        cap = StrokeCap.Round,
        strokeWidth = dotWidth.toPx()
    )
} else {
    drawLine(
        color = lineColor,
        start = Offset(0f, 0f),
        end = Offset(0f, size.height),
        cap = StrokeCap.Round,
        strokeWidth = solidWidth.toPx()
    )
}