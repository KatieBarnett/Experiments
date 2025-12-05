package dev.katiebarnett.experiments.holiday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appSplashScreenManager: AppSplashScreenManager

    override fun onCreate(savedInstanceState: Bundle?) {
//        appSplashScreenManager.getThemedSplashScreenResId()?.let {
//
//        }
//        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            ExperimentsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
//                        val checkedState = remember { mutableStateOf(viewModel.darkModeIcon.value == IconStatus.DARK) }
//                        Text(text = "Dark mode icon")
//                        Switch(checked = checkedState.value ?: false, onCheckedChange = {
//                            viewModel.setDarkModeIcon(packageManager, it)
//                            checkedState.value = it
//                        })
                    }
                }
            }
        }
    }
}

