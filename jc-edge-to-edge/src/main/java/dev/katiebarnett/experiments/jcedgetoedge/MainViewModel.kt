package dev.katiebarnett.experiments.jcedgetoedge

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {
    
    val items = flowOf(listOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20))

}