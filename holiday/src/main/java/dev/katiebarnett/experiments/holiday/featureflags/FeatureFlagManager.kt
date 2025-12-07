package dev.katiebarnett.experiments.holiday.featureflags

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

class FeatureFlagManager @Inject constructor(
    private val rocketFlagService: RocketFlagService,
    private val featureFlagStorage: FeatureFlagStore,
) {

    companion object {
        val featureFlagIds = listOf(
            "sq26LQ2XYrU9RMLh1GMa" // Enable Christmas Theme
        )
    }

    suspend fun syncFeatureFlags() {
        coroutineScope {
            featureFlagIds.map { id ->
                async(Dispatchers.IO) {
                    try {
                        rocketFlagService.getFeatureFlag(id).execute().body()
                    } catch (e: Exception) {
                        Timber.e(e, "Failed to fetch feature flag with id: $id")
                        null
                    }
                }
            }.mapNotNull { it.await() }.forEach { featureFlag ->
                featureFlagStorage.edit { preferences ->
                    preferences[stringPreferencesKey(featureFlag.id)] = Json.encodeToString(featureFlag)
                }
            }
        }
        Timber.d("Feature flag sync complete")
    }
}
