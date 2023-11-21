package dev.katiebarnett.experiments.jccardflip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.katiebarnett.experiments.core.theme.Dimen
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme
import dev.katiebarnett.experiments.jccardflip.components.Stack
import dev.katiebarnett.experiments.jccardflip.models.Card

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val position by viewModel.position.observeAsState(0)
            val advancePositionEnabled by remember {
                derivedStateOf {
                    viewModel.cardStackSize > position + 1
                }
            }
            viewModel.initialiseGame(System.currentTimeMillis(), position)
            ExperimentsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        cardStack = viewModel.cardStack,
                        position = position,
                        advancePosition = { viewModel.advancePosition() },
                        advancePositionEnabled = advancePositionEnabled
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    cardStack: List<Card>,
    position: Int,
    advancePosition: () -> Unit,
    advancePositionEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimen.spacing),
        modifier = modifier.padding(Dimen.spacing)
    ) {
        Stack(deck = cardStack, position = position, Modifier.weight(0.4f))
        Button(onClick = advancePosition, enabled = advancePositionEnabled) {
            Text(text = stringResource(id = R.string.button_flip))
        }
        Spacer(modifier = Modifier.weight(0.6f))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExperimentsTheme {
        MainScreen(cardStack = deck, position = 3, advancePosition = { })
    }
}
