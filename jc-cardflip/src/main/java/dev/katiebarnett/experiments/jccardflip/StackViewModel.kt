package dev.katiebarnett.experiments.jccardflip

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.katiebarnett.experiments.jccardflip.models.Card
import javax.inject.Inject

@HiltViewModel
class StackViewModel @Inject constructor() : ViewModel() {
    
    private lateinit var deck: List<Card>
    private var position: Int = 0
    
    val leftStackTop
        get() = deck.getOrNull(position + 1)
    
    val rightStackTop
        get() = deck.getOrNull(position - 1)

    val flipCard
        get() = deck.getOrNull(position)

    fun setPosition(newPosition: Int) {
        position = newPosition
    }

    fun setDeck(newDeck: List<Card>) {
        deck = newDeck
    }
}