package dev.katiebarnett.experiments.jccardflip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.katiebarnett.experiments.jccardflip.models.Card
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _cardStack = MutableLiveData(deck.shuffled())
    val cardStack: LiveData<List<Card>> = _cardStack
    
    private val _position = MutableLiveData(-1)
    val position: LiveData<Int> = _position

    val advancePositionEnabled = Transformations.map(position) {
        (cardStack.value?.size ?: 0) > it + 1
    }
    
    open fun advancePosition() {
        val newPosition = (_position.value ?: 0) + 1
        _position.postValue(newPosition)
    }
}