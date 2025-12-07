package dev.katiebarnett.experiments.holiday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme
import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlagManager
import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlagStore
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val appSplashScreenManager: AppSplashScreenManager by lazy {
        AppSplashScreenManager(FeatureFlagStore(@ApplicationContext this))
    }

    @Inject
    lateinit var featureFlagManager: FeatureFlagManager

    override fun onCreate(savedInstanceState: Bundle?) {
        appSplashScreenManager.getThemedSplashScreenResId()?.let {
            theme = it
        }
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val featureFlags by viewModel.featureFlagFlow.collectAsStateWithLifecycle(mapOf())
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column {
                            Text(
                                text = "Hello world!",
                                style = MaterialTheme.typography.displayLarge
                            )
                            Text(
                                text = "Christmas theme toggle is now: ${featureFlags[FeatureFlagManager.FLAG_ENABLE_CHRISTMAS_THEME]?.enabled}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}
