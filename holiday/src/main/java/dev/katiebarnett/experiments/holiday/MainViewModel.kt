package dev.katiebarnett.experiments.holiday

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.katiebarnett.experiments.holiday.toggles.FeatureFlagManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(
    private val featureFlagManager: FeatureFlagManager,
) : ViewModel(), DefaultLifecycleObserver {


    companion object {
        private const val ICON_STATUS_PREF_KEY = "ICON_STATUS"
    }

    init {
        fetchFeatureFlags()
    }

    fun fetchFeatureFlags() {
        viewModelScope.launch {
            featureFlagManager.syncFeatureFlags()
        }
    }

//    val darkModeIcon = MutableLiveData(
//        AppIconManager.IconStatus.valueOf(
//        sharedPreferences.getString(ICON_STATUS_PREF_KEY, DEFAULT.name) ?: DEFAULT.name)
//    )
//
//    fun setDarkModeIcon(packageManager: PackageManager, darkMode: Boolean) {
//        val newStatus = if (darkMode) {
//            DARK
//        } else {
//            DEFAULT
//        }
//        sharedPreferences.edit().putString(ICON_STATUS_PREF_KEY, newStatus.name).apply()
//        darkModeIcon.postValue(newStatus)
//        updateAppIcon(packageManager, newStatus)
//    }


}