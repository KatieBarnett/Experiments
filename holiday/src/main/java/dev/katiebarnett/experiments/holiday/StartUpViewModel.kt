package dev.katiebarnett.experiments.holiday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlagManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartUpViewModel @Inject constructor(
    private val featureFlagManager: FeatureFlagManager,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun syncFeatureFlags() {
        viewModelScope.launch {
            featureFlagManager.syncFeatureFlags()
            _isLoading.value = false
        }
    }
}
