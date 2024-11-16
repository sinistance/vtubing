package io.github.sinistance.vtubing.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.sinistance.vtubing.bus.EventBus
import io.github.sinistance.vtubing.bus.MainEvent
import io.github.sinistance.vtubing.people.domain.usecase.PeopleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val peopleUseCase: PeopleUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState

    init {
        EventBus.post(MainEvent(title = _uiState.value.title))
        fetchPeople()
    }

    fun fetchPeople() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                loading = true
            )
            val people = peopleUseCase.getPeople()
            _uiState.value = uiState.value.copy(
                people = people,
                loading = false,
            )
        }
    }
}