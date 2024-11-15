package io.github.sinistance.vtubing.home.presentation

import androidx.lifecycle.ViewModel
import io.github.sinistance.vtubing.bus.MainEvent
import io.github.sinistance.vtubing.bus.EventBus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState

    init {
        EventBus.post(MainEvent(title = _uiState.value.title))
    }
}