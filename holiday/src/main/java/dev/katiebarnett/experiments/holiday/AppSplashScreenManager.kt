package dev.katiebarnett.experiments.holiday

import dev.katiebarnett.experiments.holiday.toggles.FeatureFlagManager
import javax.inject.Inject

class AppSplashScreenManager @Inject constructor(
    private val featureFlagManager: FeatureFlagManager,
) {

    enum class SplashScreenStatus {
        DEFAULT, CHRISTMAS
    }

    fun getSplashScreenStatus() = if (featureFlagManager.getFeatureFlag(
            FeatureFlagManager.FLAG_ENABLE_CHRISTMAS_THEME
        )?.enabled == true
    ) {
        SplashScreenStatus.CHRISTMAS
    } else {
        SplashScreenStatus.DEFAULT
    }

    fun getThemedSplashScreenResId() =
        when (getSplashScreenStatus()) {
            SplashScreenStatus.DEFAULT ->false
            SplashScreenStatus.CHRISTMAS -> true
        }
}
