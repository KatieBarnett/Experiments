package dev.katiebarnett.experiments.holiday

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import javax.inject.Inject

class AppIconManager @Inject constructor(
    val context: Context,
) {

    enum class IconStatus {
        DEFAULT, CHRISTMAS
    }

    private fun shouldShowIcon(iconStatus: IconStatus) = when (iconStatus) {
        IconStatus.DEFAULT -> false
        IconStatus.CHRISTMAS -> true
    }

    fun updateAppIconForThemedCampaign() {
        for (icon in IconStatus.entries) {
            val action = if (shouldShowIcon(icon)) {
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            } else {
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            }
            context.packageManager.setComponentEnabledSetting(
                ComponentName(
                    BuildConfig.APPLICATION_ID,
                    "${BuildConfig.APPLICATION_ID}.${icon.name}"
                ),
                action,
                PackageManager.DONT_KILL_APP
            )
        }
    }
}
