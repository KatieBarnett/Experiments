package dev.katiebarnett.experiments.holiday.toggles

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.katiebarnett.experiments.holiday.di.FeatureFlagDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

class FeatureFlagManager @Inject constructor(
    private val rocketFlagService: RocketFlagService,
    @FeatureFlagDataStore private val featureFlagDataStore: DataStore<Preferences>,
) {

    companion object {
        val featureFlagIds = listOf(
            "sq26LQ2XYrU9RMLh1GMa" // Enable Christmas Theme
        )

        const val FLAG_ENABLE_CHRISTMAS_THEME = "EnableChristmasTheme"
    }

    val featureFlagFlow: Flow<Map<String, FeatureFlag>> = featureFlagDataStore.data.map { preferences ->
        preferences.asMap().values
            .filterIsInstance<String>()
            .mapNotNull { value ->
                try {
                    Json.decodeFromString<FeatureFlag>(value)
                } catch (e: Exception) {
                    Timber.e(e, "Failed to parse feature flag")
                    null
                }
            }
            .associateBy { it.name }
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
                featureFlagDataStore.edit { preferences ->
                    preferences[stringPreferencesKey(featureFlag.id)] = Json.encodeToString(featureFlag)
                }
            }
        }
        Timber.d("Feature flag sync complete")
    }

    fun getFeatureFlag(name: String): FeatureFlag? = runBlocking {
        featureFlagFlow.first()[name]
    }
}
