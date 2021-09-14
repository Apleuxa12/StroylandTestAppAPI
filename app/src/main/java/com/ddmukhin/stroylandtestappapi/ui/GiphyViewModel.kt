package com.ddmukhin.stroylandtestappapi.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddmukhin.stroylandtestappapi.remote.GiphyRepository
import com.ddmukhin.stroylandtestappapi.ui.data.Giphy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository
) : ViewModel() {

    sealed class GiphyAdapterState {
        object Loading : GiphyAdapterState()
        data class Success(val giphyAdapter: GiphyAdapter) : GiphyAdapterState()
        object Error : GiphyAdapterState()
    }

    private val _state = MutableStateFlow<GiphyAdapterState?>(null)
    val state = _state.asStateFlow()

    fun search(query: String) {
        if (query.isBlank())
            return
        _state.value = GiphyAdapterState.Loading
        viewModelScope.launch {
            delay(500)
            val result = giphyRepository.search(query)
            if (result != null) {
                _state.value = GiphyAdapterState.Success(GiphyAdapter(result))
            } else
                _state.value = GiphyAdapterState.Error
        }
    }

}