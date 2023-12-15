package dev.katiebarnett.experiments.jcedgetoedge

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.katiebarnett.experiments.jcedgetoedge.data.facts
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {
    
    val items = flowOf(facts)

}