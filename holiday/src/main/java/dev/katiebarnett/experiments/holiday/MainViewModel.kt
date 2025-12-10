package dev.katiebarnett.experiments.holiday

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlagStore
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val featureFlagStore by lazy { FeatureFlagStore.getInstance(context) }

    val featureFlagFlow = featureFlagStore.featureFlagFlow
}
