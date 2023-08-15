package dev.katiebarnett.experiments.jcmodifiers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme
import dev.katiebarnett.experiments.jcmodifiers.components.StateButtons
import dev.katiebarnett.experiments.jcmodifiers.examples.ComplexModifierScene
import dev.katiebarnett.experiments.jcmodifiers.examples.FirstSimplificationModifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExperimentsTheme {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent() {
    var nighttime by remember { mutableStateOf(false) }
    var torchon by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        modifier = Modifier.padding(16.dp)
    ) {
        StateButtons(
            nighttime = nighttime,
            torchon = torchon,
            onNightClick = { value -> nighttime = value },
            onTorchClick = { value -> torchon = value }
        )
        val sceneModifier = Modifier.heightIn(max = 250.dp).weight(1f).aspectRatio(1f)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ComplexModifierScene(nighttime, torchon, modifier = sceneModifier)
            FirstSimplificationModifier(nighttime, torchon, modifier = sceneModifier)
//        ConditionalModifierFirst(nighttime, torchon, modifier = sceneModifier)
//            ConditionalModifierSecond(nighttime, torchon, modifier = sceneModifier)
//            NullConditionalModifier(nighttime, torchon, backgroundColor = Color.Grey, modifier = sceneModifier)
        }
    }
}

@Composable
fun DefaultPreview() {
    ExperimentsTheme {
        MainContent()
    }
}
