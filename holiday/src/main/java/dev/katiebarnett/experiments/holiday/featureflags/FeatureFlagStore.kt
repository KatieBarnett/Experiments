package dev.katiebarnett.experiments.holiday.featureflags

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import timber.log.Timber

// Because I want to be able to access these values before dependency injection happens, this can't
// be an injected class
class FeatureFlagStore private constructor(
    @ApplicationContext private val appContext: Context,
) {

    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }
        ),
        migrations = listOf(SharedPreferencesMigration(appContext, DATA_STORE_KEY)),
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        produceFile = { appContext.preferencesDataStoreFile(DATA_STORE_KEY) }
    )

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

        private const val DATA_STORE_KEY = "feature_flags"

        const val FLAG_ENABLE_CHRISTMAS_THEME = "EnableChristmasTheme"

        @Volatile
        private var INSTANCE: FeatureFlagStore? = null

        fun getInstance(context: Context): FeatureFlagStore {
            return INSTANCE ?: synchronized(this) {
                val instance = FeatureFlagStore(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}
