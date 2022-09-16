package dev.katiebarnett.experiments.kotest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// Wrap our API results so we can avoid using try/catch in the ViewModel
sealed class Result<out T> {
    data class Error<T>(val message: String): Result<T>()
    data class Success<T>(val data: T): Result<T>()
}

data class CyberImplant(val name: String)

interface Api {
    suspend fun getCyberImplants(): List<CyberImplant>
}

class CyberImplantListViewModel(val api: Api): ViewModel() {

    val data = MutableLiveData<List<CyberImplant>>()

    fun loadData() {
        viewModelScope.launch {
            data.value = api.getCyberImplants()
        }
    }
}