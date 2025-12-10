package dev.katiebarnett.experiments.holiday

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlagStore
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    featureFlagStore: FeatureFlagStore,
) : ViewModel() {
    val featureFlagFlow = featureFlagStore.featureFlagFlow
}
