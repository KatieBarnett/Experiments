package dev.katiebarnett.experiments.holiday

import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlagStore

class AppSplashScreenManager(
    private val featureFlagStore: FeatureFlagStore
) {

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
