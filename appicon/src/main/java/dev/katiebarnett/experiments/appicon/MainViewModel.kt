package dev.katiebarnett.experiments.appicon

import android.content.ComponentName
import android.content.DataStore
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.katiebarnett.experiments.appicon.MainViewModel.IconStatus.DARK
import dev.katiebarnett.experiments.appicon.MainViewModel.IconStatus.DEFAULT
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(
    private val DataStore: DataStore
) : ViewModel(), DefaultLifecycleObserver {

    enum class IconStatus {
        DEFAULT, DARK
    }

    companion object {
        private const val ICON_STATUS_PREF_KEY = "ICON_STATUS"
    }

    val darkModeIcon = MutableLiveData(IconStatus.valueOf(
        DataStore.getString(ICON_STATUS_PREF_KEY, DEFAULT.name) ?: DEFAULT.name)
    )

    fun setDarkModeIcon(packageManager: PackageManager, darkMode: Boolean) {
        val newStatus = if (darkMode) {
            DARK
        } else {
            DEFAULT
        }
        DataStore.edit().putString(ICON_STATUS_PREF_KEY, newStatus.name).apply()
        darkModeIcon.postValue(newStatus)
        updateAppIcon(packageManager, newStatus)
    }

    fun updateAppIcon(packageManager: PackageManager, status: IconStatus) {
        for (value in IconStatus.values()) {
            val action = if (value == status) {
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            } else {
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            }
            packageManager.setComponentEnabledSetting(
                ComponentName(BuildConfig.APPLICATION_ID, "${BuildConfig.APPLICATION_ID}.${value.name}"),
                action, PackageManager.DONT_KILL_APP
            )
        }
    }
}