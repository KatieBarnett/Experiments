package dev.katiebarnett.experiments.holiday

import android.content.Context
import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlagStore

class AppSplashScreenManager(
    private val context: Context
) {

    private val featureFlagStore by lazy { FeatureFlagStore.getInstance(context) }

    enum class SplashScreenStatus {
        DEFAULT, CHRISTMAS
    }

    fun getSplashScreenStatus() = if (featureFlagStore.getFeatureFlag(
            FeatureFlagStore.FLAG_ENABLE_CHRISTMAS_THEME
        )?.enabled == true
    ) {
        SplashScreenStatus.CHRISTMAS
    } else {
        SplashScreenStatus.DEFAULT
    }

    fun getThemedSplashScreenResId() =
        when (getSplashScreenStatus()) {
            SplashScreenStatus.DEFAULT -> R.style.Theme_Experiments_Splash
            SplashScreenStatus.CHRISTMAS -> R.style.Theme_Experiments_Splash_Christmas
        }
}
