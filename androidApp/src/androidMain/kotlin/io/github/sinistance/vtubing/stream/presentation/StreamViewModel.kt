package io.github.sinistance.vtubing.stream.presentation

import androidx.lifecycle.ViewModel
import io.github.sinistance.vtubing.bus.EventBus
import io.github.sinistance.vtubing.bus.MainEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StreamViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StreamUiState())
    val uiState: StateFlow<StreamUiState> get() = _uiState

    init {
        EventBus.post(MainEvent(title = null))
    }

    fun fetchStream() {
        _uiState.value = uiState.value.copy(
            loading = true,
        )
    }

    fun streamReady() {
        _uiState.value = uiState.value.copy(
            loading = false,
        )
    }
}