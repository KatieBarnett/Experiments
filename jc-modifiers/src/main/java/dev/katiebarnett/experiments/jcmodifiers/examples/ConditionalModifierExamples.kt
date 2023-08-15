package dev.katiebarnett.experiments.jcmodifiers.examples

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.katiebarnett.experiments.jcmodifiers.R
import dev.katiebarnett.experiments.jcmodifiers.R.drawable
import dev.katiebarnett.experiments.jcmodifiers.components.ModifierScene
import dev.katiebarnett.experiments.jcmodifiers.components.Night
import dev.katiebarnett.experiments.jcmodifiers.components.Sunlight
import dev.katiebarnett.experiments.jcmodifiers.components.TorchLight
import dev.katiebarnett.experiments.jcmodifiers.util.conditional
import dev.katiebarnett.experiments.jcmodifiers.util.nullConditional

@Composable
fun ComplexModifierScene(
    nighttime: Boolean,
    torchon: Boolean,
    modifier: Modifier = Modifier
) {
    val boxModifier = if (nighttime && torchon) {
        modifier
            .clip(CircleShape)
            .fillMaxHeight()
            .aspectRatio(1f)
            .background(TorchLight)
            .shadow(elevation = 4.dp, shape = CircleShape)
    } else if (!nighttime) {
        modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .background(Sunlight)
    } else {
        modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .background(Night)
            .blur(20.dp)
    }

    Box(boxModifier) {
        Image(painterResource(R.drawable.outline_yard_24), "Scene", Modifier.fillMaxSize())
    }
}

@Composable
fun FirstSimplificationModifier(
    nighttime: Boolean,
    torchon: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (nighttime && torchon) {
        TorchLight
    } else if (!nighttime) {
        Sunlight
    } else {
        Night
    }
    val boxModifier = modifier.background(backgroundColor)
        .fillMaxHeight()
        .aspectRatio(1f)
    val updatedModifier = if (nighttime && torchon) {
        boxModifier
            .clip(CircleShape)
            .shadow(elevation = 4.dp, shape = CircleShape)
    } else if (!nighttime) {
        boxModifier
    } else {
        boxModifier
            .blur(20.dp)
    }

    Box(updatedModifier) {
        Image(painterResource(R.drawable.outline_yard_24), "Scene", Modifier.fillMaxSize())
    }
}

@Composable
fun ConditionalModifierFirst(
    nighttime: Boolean,
    torchon: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (nighttime && torchon) {
        TorchLight
    } else if (!nighttime) {
        Sunlight
    } else {
        Night
    }

    val boxModifier = modifier
        .conditional(nighttime && torchon, {
            clip(CircleShape)
        })
        .fillMaxHeight()
        .aspectRatio(1f)
        .background(backgroundColor)
        .conditional(nighttime && torchon, {
            shadow(elevation = 4.dp, shape = CircleShape)
        })
        .conditional(nighttime && !torchon, {
            blur(20.dp)
        })

    Box(boxModifier) {
        Image(painterResource(drawable.outline_yard_24), "Scene", Modifier.fillMaxSize())
    }
}

@Composable
fun ConditionalModifierSecond(
    nighttime: Boolean,
    torchon: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (nighttime && torchon) {
        TorchLight
    } else if (!nighttime) {
        Sunlight
    } else {
        Night
    }

    val boxModifier = modifier
        .conditional(nighttime && torchon, ifTrue = {
            clip(CircleShape)
        })
        .fillMaxHeight()
        .aspectRatio(1f)
        .background(backgroundColor)
        .conditional(nighttime, {
            conditional(
                condition = torchon,
                ifTrue = {
                    shadow(elevation = 4.dp, shape = CircleShape)
                },
                ifFalse = {
                    blur(20.dp)
                }
            )
        })

    Box(boxModifier) {
        Image(painterResource(R.drawable.outline_yard_24), "Scene", Modifier.fillMaxSize())
    }
}

@Composable
fun NullConditionalModifier(
    nighttime: Boolean,
    torchon: Boolean,
    backgroundColor: Color?,
    modifier: Modifier = Modifier
) {
    val boxModifier = modifier
        .conditional(nighttime && torchon, ifTrue = {
            clip(CircleShape)
        })
        .fillMaxHeight()
        .aspectRatio(1f)
        .nullConditional(backgroundColor, {
            background(it)
        })
        .conditional(nighttime, {
            conditional(
                condition = torchon,
                ifTrue = {
                    shadow(elevation = 4.dp, shape = CircleShape)
                },
                ifFalse = {
                    blur(20.dp)
                }
            )
        })

    Box(boxModifier) {
        Image(painterResource(R.drawable.outline_yard_24), "Scene", Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun ComplexModifierPreview() {
    ModifierScene(scene = { nighttime, torchon ->
        ComplexModifierScene(nighttime, torchon)
    })
}
