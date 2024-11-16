package io.github.sinistance.vtubing.people.data

import io.github.sinistance.vtubing.people.data.mapper.PeopleDataMapper
import io.github.sinistance.vtubing.people.data.remote.PeopleService
import io.github.sinistance.vtubing.people.domain.entity.Person
import io.github.sinistance.vtubing.people.domain.repository.PeopleRepository

class PeopleRepositoryImpl(
    private val service: PeopleService,
    private val mapper: PeopleDataMapper,
) : PeopleRepository {
    override suspend fun getPeople(): List<Person> {
        return service.fetchPeople().map { mapper.mapToEntity(id = it.id, name = it.name) }
    }
}