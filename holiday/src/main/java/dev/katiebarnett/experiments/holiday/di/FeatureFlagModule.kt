package dev.katiebarnett.experiments.holiday.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.katiebarnett.experiments.holiday.featureflags.RocketFlagService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit.Builder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FeatureFlagModule {

    companion object {
        const val SERVICE_URL = "https://api.rocketflag.app/v1/flags/"
    }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun provideRocketFlagService(): RocketFlagService {
        return Builder()
            .baseUrl(SERVICE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build().create(RocketFlagService::class.java)
    }

    @Provides
    @Singleton
    @FeatureFlagDataStore
    fun provideFeatureFlagDataStore(
        @ApplicationContext appContext: Context,
        @FeatureFlagFileNameKey featureFlagDataStoreKey: String,
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, featureFlagDataStoreKey)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(featureFlagDataStoreKey) }
        )
    }
}
