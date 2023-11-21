package dev.katiebarnett.experiments.jcmodifiers.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

// This is better using inline
//fun Modifier.conditional(
//    condition: Boolean,
//    ifTrue: Modifier.() -> Modifier,
//    ifFalse: (Modifier.() -> Modifier)? = null,
//): Modifier {
//    return if (condition) {
//        then(ifTrue(Modifier))
//    } else if (ifFalse != null) {
//        then(ifFalse(Modifier))
//    } else {
//        this
//    }
//}

inline fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: Modifier.() -> Modifier = { this },
): Modifier = if (condition) {
    then(ifTrue(Modifier))
} else {
    then(ifFalse(Modifier))
}

// This is better using inline
//fun <T> Modifier.nullConditional(
//    argument: T?,
//    ifNotNull: Modifier.(T) -> Modifier,
//    ifNull: (Modifier.() -> Modifier)? = null,
//): Modifier {
//    return if (argument != null) {
//        then(ifNotNull(Modifier, argument))
//    } else if (ifNull != null) {
//        then(ifNull(Modifier))
//    } else {
//        this
//    }
//}

inline fun <T> Modifier.nullConditional(
    argument: T?,
    ifNotNull: Modifier.(T) -> Modifier,
    ifNull: Modifier.() -> Modifier = { this },
): Modifier {
    return if (argument != null) {
        then(ifNotNull(this, argument))
    } else {
        then(ifNull(this))
    }
}

class GreyScaleModifier : DrawModifier {
    override fun ContentDrawScope.draw() {
        val saturationMatrix = ColorMatrix().apply { setToSaturation(0f) }
        val saturationFilter = ColorFilter.colorMatrix(saturationMatrix)
        val paint = Paint().apply {
            colorFilter = saturationFilter
        }
        drawIntoCanvas {
            it.saveLayer(Rect(0f, 0f, size.width, size.height), paint)
            drawContent()
            it.restore()
        }
    }
}

fun Modifier.greyScale() = this.then(GreyScaleModifier())

fun Modifier.disabled() = this.then(greyScale()).then(alpha(0.4f))

class ColorFilterModifier(val redScale: Float,
                          val greenScale: Float,
                          val blueScale: Float,) : DrawModifier {
    override fun ContentDrawScope.draw() {
        val saturationMatrix = ColorMatrix().apply { setToScale(redScale = redScale, greenScale = greenScale, blueScale = blueScale, alphaScale = 1f) }
        val saturationFilter = ColorFilter.colorMatrix(saturationMatrix)
        val paint = Paint().apply {
            colorFilter = saturationFilter
        }
        drawIntoCanvas {
            it.saveLayer(Rect(0f, 0f, size.width, size.height), paint)
            drawContent()
            it.restore()
        }
    }
}

fun Modifier.colorfilter(
    redScale: Float,
    greenScale: Float,
    blueScale: Float,
) = this.then(ColorFilterModifier(redScale, greenScale, blueScale))
