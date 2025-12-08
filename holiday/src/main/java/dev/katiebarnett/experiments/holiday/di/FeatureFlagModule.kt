package dev.katiebarnett.experiments.holiday.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.katiebarnett.experiments.holiday.featureflags.RocketFlagService
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

    @Singleton
    @Provides
    fun provideRocketFlagService(): RocketFlagService {
        return Builder()
            .baseUrl(SERVICE_URL)
            .addConverterFactory(
                Json {
                    ignoreUnknownKeys = true
                }.asConverterFactory("application/json".toMediaType())
            )
            .build().create(RocketFlagService::class.java)
    }
}
