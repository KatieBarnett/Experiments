package dev.katiebarnett.experiments.holiday.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FeatureFlagSharedPreferencesFileNameKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FeatureFlagSharedPreferences

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppSharedPreferencesFileNameKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppSharedPreferences

@Module
@InstallIn(SingletonComponent::class)
object QualifiersModule {

    @Provides
    @FeatureFlagSharedPreferencesFileNameKey
    fun provideFeatureFlagSharedPreferencesFileNameKey() = "feature_flags"

    @Provides
    @AppSharedPreferencesFileNameKey
    fun provideAppSharedPreferencesFileNameKey() = "app_shared_preferences"
}
