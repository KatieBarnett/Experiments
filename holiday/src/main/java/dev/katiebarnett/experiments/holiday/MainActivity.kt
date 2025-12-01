package dev.katiebarnett.experiments.holiday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.katiebarnett.experiments.holiday.MainViewModel.IconStatus
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            ExperimentsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        val checkedState = remember { mutableStateOf(viewModel.darkModeIcon.value == IconStatus.DARK) }
                        Text(text = "Dark mode icon")
                        Switch(checked = checkedState.value ?: false, onCheckedChange = {
                            viewModel.setDarkModeIcon(packageManager, it)
                            checkedState.value = it
                        })
                    }
                }
            }
        }
    }
}

