package dev.katiebarnett.experiments.holiday

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.HiltAndroidApp
import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlagStore
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class TreeApplication : Application() {

    @Inject
    lateinit var appIconManager: AppThemeManager

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        registerLifecycleObserver()
    }

    // Need lifecycle process dependency
    private fun registerLifecycleObserver() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                owner.lifecycleScope.launch {
                    appIconManager.updateAppAliasForThemeMode()
                }
            }
        })
    }
}
