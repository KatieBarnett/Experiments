package dev.katiebarnett.experiments.holiday.featureflags

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

class FeatureFlagManager @Inject constructor(
    private val rocketFlagService: RocketFlagService,
    @ApplicationContext private val context: Context,
) {

    private val featureFlagStore by lazy { FeatureFlagStore.getInstance(context) }

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
                featureFlagStore.edit { preferences ->
                    preferences[stringPreferencesKey(featureFlag.id)] =
                        Json.encodeToString(featureFlag)
                }
            }
        }
        Timber.d("Feature flag sync complete")
    }
}
