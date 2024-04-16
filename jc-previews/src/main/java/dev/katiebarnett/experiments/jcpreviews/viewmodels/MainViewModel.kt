package dev.katiebarnett.experiments.jcpreviews.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    val someViewModelFlow = MutableStateFlow(listOf("a", "b", "c"))
}
