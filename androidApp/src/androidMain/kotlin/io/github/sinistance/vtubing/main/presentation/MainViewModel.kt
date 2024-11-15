package io.github.sinistance.vtubing.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.sinistance.vtubing.bus.MainEvent
import io.github.sinistance.vtubing.bus.EventBus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> get() = _uiState

    init {
        EventBus.subscribe<MainEvent>(viewModelScope) {
            _uiState.value = _uiState.value.copy(
                title = it.title
            )
        }
    }

    fun showBottomNav(show: Boolean) {
        _uiState.value = _uiState.value.copy(
            showBottomNav = show
        )
    }
}