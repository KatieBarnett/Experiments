package dev.katiebarnett.experiments.jctext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme
import dev.katiebarnett.experiments.jctext.components.FontScalePreviews
import dev.katiebarnett.experiments.jctext.components.NonResizingTextInteger
import dev.katiebarnett.experiments.jctext.components.NonResizingTextSp
import dev.katiebarnett.experiments.jctext.components.RegularResizingText

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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Font scale: ${LocalDensity.current.fontScale}", fontWeight = FontWeight.Bold)
        Text("Density: ${LocalDensity.current.density}", fontWeight = FontWeight.Bold)
        RegularResizingText(textToDisplay = "This is regular resizing text")
        NonResizingTextInteger(textToDisplay = "This is non-resizing text - using a integer value")
        NonResizingTextSp(textToDisplay = "This is non-resizing text - using an sp value")
    }
}

@FontScalePreviews
@Composable
fun DefaultPreview() {
    ExperimentsTheme {
        MainContent()
    }
}