package io.github.sinistance.vtubing.broadcast.presentation

import androidx.lifecycle.ViewModel
import com.google.mediapipe.tasks.vision.core.RunningMode
import io.github.sinistance.vtubing.bus.EventBus
import io.github.sinistance.vtubing.bus.MainEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BroadcastViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BroadcastUiState())
    val uiState: StateFlow<BroadcastUiState> = _uiState.asStateFlow()

    init {
        EventBus.post(MainEvent(title = null))
    }
}