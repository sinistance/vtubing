package io.github.sinistance.vtubing.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> get() = _uiState

    fun login() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            loading = true,
        )
        delay(2000)
        _uiState.value = _uiState.value.copy(
            loading = false,
            loginSuccess = true,
        )
    }
}