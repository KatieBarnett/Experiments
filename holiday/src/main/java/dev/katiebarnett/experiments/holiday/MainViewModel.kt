package dev.katiebarnett.experiments.holiday

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.katiebarnett.experiments.holiday.toggles.FeatureFlagManager
import dev.katiebarnett.experiments.holiday.toggles.FeatureFlagManager.Companion.FLAG_ENABLE_CHRISTMAS_THEME
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(
    private val featureFlagManager: FeatureFlagManager,
) : ViewModel() {

    companion object {
        private const val ICON_STATUS_PREF_KEY = "ICON_STATUS"
    }

    // For debugging and display
    val featureFlagFlow = featureFlagManager.featureFlagFlow


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
//        DataStore.getString(ICON_STATUS_PREF_KEY, DEFAULT.name) ?: DEFAULT.name)
//    )
//
//    fun setDarkModeIcon(packageManager: PackageManager, darkMode: Boolean) {
//        val newStatus = if (darkMode) {
//            DARK
//        } else {
//            DEFAULT
//        }
//        DataStore.edit().putString(ICON_STATUS_PREF_KEY, newStatus.name).apply()
//        darkModeIcon.postValue(newStatus)
//        updateAppIcon(packageManager, newStatus)
//    }
}
