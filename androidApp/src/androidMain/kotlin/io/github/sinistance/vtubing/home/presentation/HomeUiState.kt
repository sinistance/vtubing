package io.github.sinistance.vtubing.home.presentation

import io.github.sinistance.vtubing.people.domain.entity.Person

data class HomeUiState(
    val title: String = "Home",
    val loading: Boolean = false,
    val people: List<Person> = emptyList(),
)