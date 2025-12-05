package dev.katiebarnett.experiments.holiday.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    @AppSharedPreferences
    fun provideAppSharedPreferences(
        @ApplicationContext context: Context,
        @AppSharedPreferencesFileNameKey appSharedPreferencesKey: String,
    ): SharedPreferences {
        return context.getSharedPreferences(appSharedPreferencesKey, Context.MODE_PRIVATE)
    }
}