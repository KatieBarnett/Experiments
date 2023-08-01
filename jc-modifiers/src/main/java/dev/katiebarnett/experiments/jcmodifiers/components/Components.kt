package dev.katiebarnett.experiments.jcmodifiers.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.katiebarnett.experiments.core.util.conditional
import dev.katiebarnett.experiments.core.util.nullConditional
import dev.katiebarnett.experiments.jcmodifiers.R

val Sunlight = Color(0x7793CDE7)
val TorchLight = Color(0x77E7DE93)
val Night = Color(0xEF02062D)

@Composable
fun ModifierScene(
    scene: @Composable (nighttime: Boolean, torchon: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var nighttime by remember { mutableStateOf(false) }
    var torchon by remember { mutableStateOf(false) }
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        StateButtons(
            nighttime = nighttime,
            torchon = torchon,
            onNightClick = { value -> nighttime = value },
            onTorchClick = { value -> torchon = value }
        )
        scene(nighttime = nighttime, torchon = torchon)
    }
}

@Composable
fun StateButtons(nighttime: Boolean, torchon: Boolean, onNightClick: (Boolean) -> Unit, onTorchClick: (Boolean) -> Unit) {
    val dayStateDrawable by remember(nighttime) {
        derivedStateOf {
            if (nighttime) {
                R.drawable.outline_nightlight_24
            } else {
                R.drawable.outline_light_mode_24
            }
        }
    }
    val lightDrawable by remember(torchon) {
        derivedStateOf {
            if (torchon) {
                R.drawable.outline_flashlight_on_24
            } else {
                R.drawable.outline_flashlight_off_24
            }
        }
    }
    Row {
        IconButton(onClick = { onNightClick.invoke(!nighttime) }) {
            Icon(painterResource(dayStateDrawable), "Nighttime")
        }
        IconButton(onClick = { onTorchClick.invoke(!torchon) }) {
            Icon(painterResource(lightDrawable), "Light")
        }
    }
}

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
        Image(painterResource(R.drawable.outline_yard_24), "Scene", Modifier.fillMaxSize())
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
