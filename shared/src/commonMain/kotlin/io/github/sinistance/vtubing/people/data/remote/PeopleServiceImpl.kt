package io.github.sinistance.vtubing.people.data.remote

import io.github.sinistance.vtubing.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PeopleServiceImpl(
    private val httpClient: HttpClient
) : PeopleService {
    override suspend fun fetchPeople(): List<PeopleResponse.Person> {
        val path = "/people"
        val response: PeopleResponse = httpClient.get(ApiConfig.API_HOST + path).body()
        return response.results
    }

}