package dev.katiebarnett.experiments.jcmodifiers.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
