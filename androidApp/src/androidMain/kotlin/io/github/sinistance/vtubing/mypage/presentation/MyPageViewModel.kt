package io.github.sinistance.vtubing.mypage.presentation

import androidx.lifecycle.ViewModel
import io.github.sinistance.vtubing.bus.EventBus
import io.github.sinistance.vtubing.bus.MainEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyPageViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> get() = _uiState

    init {
        EventBus.post(MainEvent(title = _uiState.value.title))
    }
}