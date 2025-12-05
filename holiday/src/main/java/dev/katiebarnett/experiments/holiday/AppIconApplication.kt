package dev.katiebarnett.experiments.holiday

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class AppIconApplication : Application() {

    @Inject
    lateinit var appIconManager: AppIconManager

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        registerLifecycleObserver()
    }

    // need lifecycle process dependency
    private fun registerLifecycleObserver() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                appIconManager.updateAppIconForThemedCampaign()
            }
        })
    }
}
