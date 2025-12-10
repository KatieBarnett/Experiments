package dev.katiebarnett.experiments.holiday.featureflags

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import dev.katiebarnett.experiments.holiday.di.FeatureFlagDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

class FeatureFlagStore @Inject constructor(
    @FeatureFlagDataStore private val dataStore: DataStore<Preferences>,
) {

    val featureFlagFlow: Flow<Map<String, FeatureFlag>> = dataStore.data.map { preferences ->
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

    fun getFeatureFlag(name: String): FeatureFlag? = runBlocking {
        featureFlagFlow.first()[name]
    }

    suspend fun edit(transform: suspend (MutablePreferences) -> Unit) {
        dataStore.edit(transform)
    }

    companion object {
        const val FLAG_ENABLE_CHRISTMAS_THEME = "EnableChristmasTheme"
    }
}
