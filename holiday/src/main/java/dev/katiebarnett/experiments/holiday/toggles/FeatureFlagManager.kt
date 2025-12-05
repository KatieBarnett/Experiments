package dev.katiebarnett.experiments.holiday.toggles

import android.content.SharedPreferences
import androidx.core.content.edit
import dev.katiebarnett.experiments.holiday.di.FeatureFlagSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

class FeatureFlagManager @Inject constructor(
    private val rocketFlagService: RocketFlagService,
    @FeatureFlagSharedPreferences private val featureFlagSharedPreferences: SharedPreferences,
) {

    companion object {
        val featureFlagIds = listOf(
            "sq26LQ2XYrU9RMLh1GMa" // Enable Christmas Theme
        )
    }

    suspend fun syncFeatureFlags() {
        withContext(Dispatchers.IO) {
            coroutineScope {
                featureFlagIds.map { id ->
                    async {
                        try {
                            val flag = rocketFlagService.getFeatureFlag(id).execute().body()
                            Timber.d("Feature flag fetched: $flag")
                            flag
                        } catch (e: Exception) {
                            Timber.e(e, "Failed to fetch feature flag with id: $id")
                            null
                        }
                    }
                }.mapNotNull { it.await() }.forEach { featureFlag ->
                    featureFlagSharedPreferences.edit {
                        putString(featureFlag.id, Json.encodeToString(featureFlag))
                    }
                }
            }
            Timber.d("Feature flag sync complete")
        }
    }
}
