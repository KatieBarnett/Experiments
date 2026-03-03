package dev.katiebarnett.experiments.jctext

import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExperimentsTheme {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .safeDrawingPadding()
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Text(
                            "Change the locale to see the bug.",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            "Using LocalContext.current:",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                        HelloScreenContext("Android")
                        HelloToastContext()
                        Text(
                            "Using LocalResources.current:",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                        HelloScreenResources("Android")
                        HelloToastResources()
                    }
                }
            }
        }
    }
}

@Composable
fun HelloScreenContext(username: String) {
    val context = LocalContext.current
    Text(
        text = WelcomeFormatter(context.resources).getPersonalisedMessage(username),
        color = Color.Red,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun HelloScreenResources(username: String) {
    val resources = LocalResources.current
    Text(
        text = WelcomeFormatter(resources).getPersonalisedMessage(username),
        color = Color.Blue,
        style = MaterialTheme.typography.titleLarge
    )
}

// Notice: No @Composable annotation here!
class WelcomeFormatter(resources: Resources) {

    // This string is fetched when this class is instantiated.
    val baseWelcome: String = resources.getString(R.string.welcome_message)

    fun getPersonalisedMessage(username: String): String {
        return "$baseWelcome, $username!"
    }
}

@Composable
fun HelloToastContext() {
    val context = LocalContext.current
    Button(onClick = {
        Toast.makeText(
            context,
            "${context.getString(R.string.welcome_message)} using LocalContext.current",
            Toast.LENGTH_SHORT
        ).show()
    }) {
        Text(stringResource(R.string.button_text))
    }
}

@Composable
fun HelloToastResources() {
    val context = LocalContext.current
    val resources = LocalResources.current
    Button(onClick = {
        Toast.makeText(
            context,
            "${resources.getString(R.string.welcome_message)} using LocalResources.current",
            Toast.LENGTH_SHORT
        ).show()
    }) {
        Text(stringResource(R.string.button_text))
    }
}
