package dev.katiebarnett.experiments.holiday

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlagManager
import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlagStore
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val featureFlagManager: FeatureFlagManager,
) : ViewModel() {

    companion object {
        private const val ICON_STATUS_PREF_KEY = "ICON_STATUS"
    }

    private val featureFlagStore by lazy { FeatureFlagStore.getInstance(context) }

    // For debugging and display
    val featureFlagFlow = featureFlagStore.featureFlagFlow


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
