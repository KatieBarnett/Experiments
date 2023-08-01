package dev.katiebarnett.experiments.core.util

import androidx.compose.ui.Modifier

fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: (Modifier.() -> Modifier)? = null,
): Modifier {
    return if (condition) {
        then(ifTrue(Modifier))
    } else if (ifFalse != null) {
        then(ifFalse(Modifier))
    } else {
        this
    }
}

fun <T> Modifier.nullConditional(
    argument: T?,
    ifNotNull: Modifier.(T) -> Modifier,
    ifNull: (Modifier.() -> Modifier)? = null,
): Modifier {
    return if (argument != null) {
        then(ifNotNull(Modifier, argument))
    } else if (ifNull != null) {
        then(ifNull(Modifier))
    } else {
        this
    }
}