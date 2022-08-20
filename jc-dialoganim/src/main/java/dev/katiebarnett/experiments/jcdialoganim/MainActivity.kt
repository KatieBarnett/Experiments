package dev.katiebarnett.experiments.jcdialoganim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.katiebarnett.experiments.jcdialoganim.components.AnimatedDialog
import dev.katiebarnett.experiments.jcdialoganim.components.AnimatedDialogNoAnimatedButtonDismiss
import dev.katiebarnett.experiments.jcdialoganim.components.AnimatedEntryDialog
import dev.katiebarnett.experiments.jcdialoganim.components.RegularDialog
import dev.katiebarnett.experiments.jcdialoganim.ui.theme.ExperimentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExperimentsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(hiltViewModel())
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val isRegularDialogDisplayed by viewModel.isRegularDialogDisplayed.observeAsState(false)

    if (isRegularDialogDisplayed) {
        RegularDialog(
            buttonAction = {
                // Do something
            },
            onDismissRequest = {
                viewModel.isRegularDialogDisplayed.postValue(false)
            }
        )
    }

    val isAnimatedDialogDisplayed by viewModel.isAnimatedDialogDisplayed.observeAsState(false)

    if (isAnimatedDialogDisplayed) {
        AnimatedDialog(
            buttonAction = {
                // Do something
            },
            onDismissRequest = {
                viewModel.isAnimatedDialogDisplayed.postValue(false)
            }
        )
    }

    val isAnimatedEntryDialogDisplayed by viewModel.isAnimatedEntryDialogDisplayed.observeAsState(false)

    if (isAnimatedEntryDialogDisplayed) {
        AnimatedEntryDialog(
            buttonAction = {
                // Do something
            },
            onDismissRequest = {
                viewModel.isAnimatedEntryDialogDisplayed.postValue(false)
            }
        )
    }

    val isAnimatedNoButtonDismissDialogDisplayed by viewModel.isAnimatedNoButtonDismissDialogDisplayed.observeAsState(false)

    if (isAnimatedNoButtonDismissDialogDisplayed) {
        AnimatedDialogNoAnimatedButtonDismiss(
            buttonAction = {
                // Do something
            },
            onDismissRequest = {
                viewModel.isAnimatedNoButtonDismissDialogDisplayed.postValue(false)
            }
        )
    }

    MainContent(
        onRegularButtonClick = { viewModel.isRegularDialogDisplayed.postValue(true) }, 
        onAnimatedButtonClick = { viewModel.isAnimatedDialogDisplayed.postValue(true) },
        onAnimatedEntryButtonClick = { viewModel.isAnimatedEntryDialogDisplayed.postValue(true) },
        onAnimatedNoButtonDismissButtonClick = { viewModel.isAnimatedNoButtonDismissDialogDisplayed.postValue(true) }
    )
}

@Composable
fun MainContent(
    onRegularButtonClick: () -> Unit,
    onAnimatedButtonClick: () -> Unit,
    onAnimatedEntryButtonClick: () -> Unit,
    onAnimatedNoButtonDismissButtonClick: () -> Unit) { 
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onRegularButtonClick) {
            Text(text = "Open regular dialog")
        }
        Button(onClick = onAnimatedEntryButtonClick) {
            Text(text = "Open animated entry dialog")
        }
        Button(onClick = onAnimatedNoButtonDismissButtonClick) {
            Text(text = "Open animated dialog with no button dismiss")
        }
        Button(onClick = onAnimatedButtonClick) {
            Text(text = "Open animated dialog")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExperimentsTheme {
        MainContent({}, {}, {}, {})
    }
}