package dev.katiebarnett.experiments.jcedgetoedge

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme
import dev.katiebarnett.experiments.jcedgetoedge.components.FactDetail
import dev.katiebarnett.experiments.jcedgetoedge.components.FactItem
import dev.katiebarnett.experiments.jcedgetoedge.data.facts

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        enableEdgeToEdge()
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        setContent {
            val navController = rememberNavController()
            ExperimentsTheme {
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        ImageTopBar(navController)
                    }
                    dialog("detail",
                        dialogProperties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = true)
                    ) {
                        // TODO pass argument
                        FactDetail(facts.first())
                    }
                }

            }
        }
    }
}

@Composable
fun ImageTopBar(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = hiltViewModel()
    val items: List<Int> by viewModel.items.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            Box(modifier = Modifier) {
                Image(
                    painter = painterResource(id = R.drawable.coral_reef_15796068239iq),
                    contentDescription = "",
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(0.dp)) }
            items(facts) {
                FactItem(it, {
                    navController.navigate("detail")
                })
            }
            item { Spacer(modifier = Modifier.height(0.dp)) }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ImageTopBarPreview() {
    ExperimentsTheme {
        ImageTopBar(rememberNavController())
    }
}
