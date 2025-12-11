package dev.katiebarnett.experiments.holiday

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StartUpActivityChristmas : StartUpActivity()

@AndroidEntryPoint
open class StartUpActivity : ComponentActivity() {
    private val viewModel: StartUpViewModel by viewModels()

    @Inject
    lateinit var appThemeManager: AppThemeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        // Don't do this - it doesn't work:
        // setTheme(appThemeManager.getThemedSplashScreenResId())
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.syncFeatureFlags()

        splashScreen.setKeepOnScreenCondition {
            viewModel.isLoading.value
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    if (!isLoading) {
                        val mainActivityIntent =
                            Intent(this@StartUpActivity, MainActivity::class.java)
                        mainActivityIntent.data = intent.data
                        mainActivityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(mainActivityIntent)
                    }
                }
            }
        }
    }
}
