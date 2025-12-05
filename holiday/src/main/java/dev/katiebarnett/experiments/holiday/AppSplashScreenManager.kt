package dev.katiebarnett.experiments.holiday

import javax.inject.Inject

class AppSplashScreenManager @Inject constructor() {

    enum class SplashScreenStatus {
        DEFAULT, CHRISTMAS
    }

    fun getThemedSplashScreenResId(splashScreenStatus: SplashScreenStatus) =
        when (splashScreenStatus) {
            SplashScreenStatus.DEFAULT -> false
            SplashScreenStatus.CHRISTMAS -> true
        }
}
