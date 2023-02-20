package dev.katiebarnett.experiments.jcrefresh

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.katiebarnett.experiments.jcrefresh.data.FoxService
import dev.katiebarnett.experiments.jcrefresh.model.Fox
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.await
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    companion object {
        const val MAX_LIST_ITEMS = 10
        const val SERVICE_URL = "https://randomfox.ca/floof/"
    }

    val foxService by lazy {
        Builder()
            .baseUrl(SERVICE_URL)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
            .build().create(FoxService::class.java)
    }

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val _item = MutableStateFlow<Fox?>(null)
    val item: StateFlow<Fox?>
        get() = _item.asStateFlow()

    private val _items = MutableStateFlow<List<Fox>>(listOf())
    val items: StateFlow<List<Fox>>
        get() = _items.asStateFlow()

    init {
        refresh()
    }

    // Single item
    fun refresh() {
        viewModelScope.launch {
            async(Dispatchers.IO) {
                _item.emit(foxService.getRandomFox().await())
            }
            _isRefreshing.emit(false)
        }
    }

    // Multiple items
//    fun refresh() {
//        viewModelScope.launch {
//            val foxList = mutableListOf<Fox>()
//            (0..MAX_LIST_ITEMS - 1).map {
//                async(Dispatchers.IO) {
//                    foxList.add(foxService.getRandomFox().await())
//                }
//            }.awaitAll()
//            _items.emit(foxList)
//            _isRefreshing.emit(false)
//        }
//    }

}