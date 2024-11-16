package io.github.sinistance.vtubing.people.domain.usecase

import io.github.sinistance.vtubing.people.domain.entity.Person

interface PeopleUseCase {
    suspend fun getPeople(): List<Person>
}