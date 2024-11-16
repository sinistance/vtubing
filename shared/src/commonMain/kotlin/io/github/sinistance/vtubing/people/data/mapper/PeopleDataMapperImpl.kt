package io.github.sinistance.vtubing.people.data.mapper

import io.github.sinistance.vtubing.people.domain.entity.Person

class PeopleDataMapperImpl : PeopleDataMapper {
    override fun mapToEntity(id: String, name: String): Person {
        return Person(id = id, name = name)
    }
}