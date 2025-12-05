package dev.katiebarnett.experiments.appicon.di

import android.content.Context
import android.content.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): DataStore {
        return context.getDataStore("preferences_name", Context.MODE_PRIVATE)
    }
}