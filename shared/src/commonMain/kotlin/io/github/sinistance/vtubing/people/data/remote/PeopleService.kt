package io.github.sinistance.vtubing.people.data.remote

interface PeopleService {
    suspend fun fetchPeople(): List<PeopleResponse.Person>
}