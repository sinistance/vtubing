package io.github.sinistance.vtubing.people.data.mapper

import io.github.sinistance.vtubing.people.domain.entity.Person

interface PeopleDataMapper {
    fun mapToEntity(
        id: String,
        name: String,
    ): Person
}