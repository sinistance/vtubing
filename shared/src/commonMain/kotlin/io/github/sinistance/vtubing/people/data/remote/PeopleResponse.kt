package io.github.sinistance.vtubing.people.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeopleResponse(
    @SerialName("results")
    val results: List<Person>,
) {
    @Serializable
    data class Person(
        @SerialName("name")
        val name: String,
        @SerialName("url")
        val url: String,
    ) {
        val id = url.split("/").filterNot { it.isEmpty() }.last()
    }
}