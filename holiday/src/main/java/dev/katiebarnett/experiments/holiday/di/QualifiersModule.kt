package dev.katiebarnett.experiments.holiday.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FeatureFlagFileNameKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FeatureFlagDataStore

@Module
@InstallIn(SingletonComponent::class)
object QualifiersModule {

    @Provides
    @FeatureFlagFileNameKey
    fun provideFeatureFlagFileNameKey() = "feature_flags"
}
