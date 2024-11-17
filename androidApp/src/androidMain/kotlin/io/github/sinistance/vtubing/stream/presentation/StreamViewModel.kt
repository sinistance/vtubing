package io.github.sinistance.vtubing.stream.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.sinistance.vtubing.bus.EventBus
import io.github.sinistance.vtubing.bus.MainEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StreamViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StreamUiState())
    val uiState: StateFlow<StreamUiState> get() = _uiState

    init {
        EventBus.post(MainEvent(title = null))
    }

    fun fetchStream(url: String) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                loading = true,
            )
            delay(3000)
            _uiState.value = uiState.value.copy(
                loading = false,
            )
        }
    }
}