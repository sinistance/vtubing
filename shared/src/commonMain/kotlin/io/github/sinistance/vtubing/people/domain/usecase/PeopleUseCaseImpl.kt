package io.github.sinistance.vtubing.people.domain.usecase

import io.github.sinistance.vtubing.people.domain.entity.Person
import io.github.sinistance.vtubing.people.domain.repository.PeopleRepository

class PeopleUseCaseImpl(
    private val repository: PeopleRepository,
) : PeopleUseCase {
    override suspend fun getPeople(): List<Person> {
        return repository.getPeople()
    }
}