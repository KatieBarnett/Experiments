package dev.katiebarnett.experiments.jccardflip

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.katiebarnett.experiments.jccardflip.models.Card
import javax.inject.Inject

@HiltViewModel
class StackViewModel @Inject constructor() : ViewModel() {
    
    private lateinit var stack: List<Card>
    private var position: Int = 0
    
    val leftStackTop
        get() = stack.getOrNull(position + 1)
    
    val rightStackTop
        get() = stack.getOrNull(position - 1)

    val flipCard
        get() = stack.getOrNull(position)

    fun setPosition(newPosition: Int) {
        position = newPosition
    }

    fun setStack(newStack: List<Card>) {
        stack = newStack
    }
}