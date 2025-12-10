package dev.katiebarnett.experiments.holiday

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlag
import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlagStore
import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlagStore.Companion.FLAG_ENABLE_CHRISTMAS_THEME
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AppThemeManager @Inject constructor(
    val featureFlagStore: FeatureFlagStore,
    val context: Context,
) {

    enum class ThemeMode {
        DEFAULT, CHRISTMAS
    }

    companion object {
        fun getThemeMode(featureFlags: Map<String, FeatureFlag>): ThemeMode {
            return if (featureFlags[FLAG_ENABLE_CHRISTMAS_THEME]?.enabled == true) {
                ThemeMode.CHRISTMAS
            } else {
                ThemeMode.DEFAULT
            }
        }
    }

    suspend fun updateAppAliasForThemeMode() {
        val currentThemeMode = getThemeMode(featureFlagStore.featureFlagFlow.first())
        for (theme in ThemeMode.entries) {
            val action = if (currentThemeMode == theme) {
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            } else {
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            }
            context.packageManager.setComponentEnabledSetting(
                ComponentName(
                    BuildConfig.APPLICATION_ID,
                    "${BuildConfig.APPLICATION_ID}.${theme.name}"
                ),
                action,
                PackageManager.DONT_KILL_APP
            )
        }
    }
}
