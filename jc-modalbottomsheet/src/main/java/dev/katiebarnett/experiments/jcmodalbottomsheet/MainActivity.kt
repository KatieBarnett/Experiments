package dev.katiebarnett.experiments.jcmodalbottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExperimentsTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "My App") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                scrolledContainerColor = MaterialTheme.colorScheme.primary,
                                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                            )
                        )
//                        Box(modifier = Modifier) {
//                            Image(
//                                painter = painterResource(id = R.drawable.fruit),
//                                contentDescription = "",
//                                contentScale = ContentScale.FillWidth,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .alpha(0.5f)
//                            )
//                            Text(
//                                text = stringResource(id = R.string.app_bar),
//                                style = MaterialTheme.typography.displayLarge,
//                                modifier = Modifier
//                                    .align(Alignment.BottomStart)
//                                    .padding(8.dp)
//                            )
//                        }
                    },
                    modifier = Modifier
                ) { innerPadding ->
//                    ConfirmCloseSheet(Modifier.padding(innerPadding))
                    ExpandingContentSheet(Modifier.padding(innerPadding))
                }
            }
        }
    }
}
