package io.github.sinistance.vtubing.people.domain.repository

import io.github.sinistance.vtubing.people.domain.entity.Person

interface PeopleRepository {
    suspend fun getPeople(): List<Person>
}