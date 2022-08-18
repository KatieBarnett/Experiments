package dev.katiebarnett.experiments.jcdialoganim

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(
) : ViewModel(), DefaultLifecycleObserver {

    val isRegularDialogDisplayed = MutableLiveData(false)
    val isAnimatedDialogDisplayed = MutableLiveData(false)
    val isAnimatedEntryDialogDisplayed = MutableLiveData(false)
}