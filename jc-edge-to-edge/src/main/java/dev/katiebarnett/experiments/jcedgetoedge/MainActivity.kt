package dev.katiebarnett.experiments.jcedgetoedge

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout.LayoutParams
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme
import dev.katiebarnett.experiments.jcedgetoedge.components.FactDetail
import dev.katiebarnett.experiments.jcedgetoedge.components.FactItem
import dev.katiebarnett.experiments.jcedgetoedge.data.ReefFact
import dev.katiebarnett.experiments.jcedgetoedge.data.facts

// @Composable
// fun getDialogWindow(): Window? = (LocalView.current.parent as? DialogWindowProvider)?.window
//
// @Composable
// fun getActivityWindow(): Window? = LocalView.current.context.getActivityWindow()

// private tailrec fun Context.getActivityWindow(): Window? =
//    when (this) {
//        is Activity -> window
//        is ContextWrapper -> baseContext.getActivityWindow()
//        else -> null
//    }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)))
//        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.parseColor("#801b1b1b")))
        setContent {
            val navController = rememberNavController()
            val viewModel: MainViewModel = hiltViewModel()
            val items: List<ReefFact> by viewModel.items.collectAsState(initial = emptyList())

            var showDialog by remember { mutableStateOf<Int?>(null) }

            ExperimentsTheme {
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(
                            facts = items,
                            onDetailClick = {
//                                showDialog = items.indexOf(it)
                                navController.navigate("detail/${items.indexOf(it)}")
                            }
                        )
                    }
                    dialog(
                        route = "detail/{index}",
                        arguments = listOf(navArgument("index") { type = NavType.IntType }),
                        dialogProperties = DialogProperties(
                            usePlatformDefaultWidth = true,
                            decorFitsSystemWindows = false
                        )
                    ) { backStackEntry ->
                        // Based on https://stackoverflow.com/a/75768025/4714860
//                        val activityWindow = getActivityWindow()
//                        val dialogWindow = getDialogWindow()
//                        val parentView = LocalView.current.parent as View
//                        SideEffect {
//                            if (activityWindow != null && dialogWindow != null) {
//                                val attributes = WindowManager.LayoutParams()
//                                attributes.copyFrom(activityWindow.attributes)
//                                attributes.type = dialogWindow.attributes.type
//                                dialogWindow.attributes = attributes
//                                parentView.layoutParams = LayoutParams(
//                                    activityWindow.decorView.width,
//                                    activityWindow.decorView.height)
//                            }
//                        }
//
//                        val systemUiController = rememberSystemUiController(getActivityWindow())
//                        val dialogSystemUiController = rememberSystemUiController(getDialogWindow())
//
//                        DisposableEffect(Unit) {
//                            systemUiController.setSystemBarsColor(color = Color.Transparent)
//                            dialogSystemUiController.setSystemBarsColor(color = Color.Transparent)
//
//                            onDispose {
// //                                systemUiController.setSystemBarsColor(color = previousSystemBarsColor)
// //                                dialogSystemUiController.setSystemBarsColor(color = previousSystemBarsColor)
//                            }
//                        }

                        val activityWindow = window
                        val dialogWindow = (LocalView.current.parent as? DialogWindowProvider)?.window
                        val parentView = LocalView.current.parent as View
                        SideEffect {
                            if (activityWindow != null && dialogWindow != null) {
                                val attributes = WindowManager.LayoutParams()
                                attributes.copyFrom(activityWindow.attributes)
                                attributes.type = dialogWindow.attributes.type
                                dialogWindow.attributes = attributes
                                parentView.layoutParams = LayoutParams(
                                    activityWindow.decorView.width,
                                    activityWindow.decorView.height
                                )
                            }
                        }

                        backStackEntry.arguments?.getInt("index")?.let {
                            FactDetail(
                                fact = items[it],
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }

//                showDialog?.let {
//                    Dialog(
//                        onDismissRequest = { showDialog = null },
//                        properties = DialogProperties(
//                            usePlatformDefaultWidth = false,
//                            decorFitsSystemWindows = false
//                        )
//                    ) {
//                        FactDetail(items[it], Modifier.fillMaxSize())
//                    }
//                }

                showDialog?.let {
                    Dialog(
                        onDismissRequest = { showDialog = null },
                        properties = DialogProperties(
                            usePlatformDefaultWidth = true,
                            decorFitsSystemWindows = false
                        )
                    ) {
                        // Step 1: Find the windows
                        val activityWindow = window
                        val dialogWindow = (LocalView.current.parent as? DialogWindowProvider)?.window
                        val parentView = LocalView.current.parent as View
                        SideEffect {
                            if (activityWindow != null && dialogWindow != null) {
                                // Step 2: Get and apply the activity attributes to the dialog parent view
                                val attributes = WindowManager.LayoutParams()
                                attributes.copyFrom(activityWindow.attributes)
                                attributes.type = dialogWindow.attributes.type
                                dialogWindow.attributes = attributes
                                parentView.layoutParams = LayoutParams(
                                    activityWindow.decorView.width,
                                    activityWindow.decorView.height
                                )
                            }
                        }

                        DisposableEffect(Unit) {
                            if (items[it].useDarkStatusBar) {
                                enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.BLUE))
                            } else {
                                enableEdgeToEdge(
                                    statusBarStyle = SystemBarStyle.light(Color.RED, Color.GREEN)
                                )
                            }

                            onDispose {
                                enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.CYAN))
                            }
                        }

                        FactDetail(
                            fact = items[it],
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    facts: List<ReefFact>,
    onDetailClick: (ReefFact) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            Box(modifier = Modifier) {
                Image(
                    painter = painterResource(id = R.drawable.clownfish),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
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
                FactItem(it, { onDetailClick.invoke(it) })
            }
            item { Spacer(modifier = Modifier.height(0.dp)) }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    ExperimentsTheme {
        MainScreen(facts, {})
    }
}
