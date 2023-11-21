package dev.katiebarnett.experiments.jccardflip

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.katiebarnett.experiments.jccardflip.models.Card
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    lateinit var cardStack: List<Card>

    private val _position = MutableLiveData(0)
    val position: LiveData<Int> = _position

    val cardStackSize: Int
        get() = cardStack.size

    fun advancePosition() {
        val newPosition = (_position.value ?: 0) + 1
        _position.postValue(newPosition)
    }

    fun initialiseGame(gameSeed: Long, position: Int) {
        // Only initialise if not already initialised
        if (!this::cardStack.isInitialized) {
            this._position.postValue(position)
            viewModelScope.launch {
                cardStack = deck.shuffled(Random(gameSeed))
            }
        }
    }
}
