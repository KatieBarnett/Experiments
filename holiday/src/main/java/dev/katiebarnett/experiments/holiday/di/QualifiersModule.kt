package dev.katiebarnett.experiments.holiday.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FeatureFlagDataStoreFileNameKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FeatureFlagDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppDataStoreFileNameKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppDataStore

@Module
@InstallIn(SingletonComponent::class)
object QualifiersModule {

    @Provides
    @FeatureFlagDataStoreFileNameKey
    fun provideFeatureFlagDataStoreFileNameKey() = "feature_flags"

    @Provides
    @AppDataStoreFileNameKey
    fun provideAppDataStoreFileNameKey() = "app_shared_preferences"
}
