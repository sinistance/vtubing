package io.github.sinistance.vtubing.people.domain.entity

data class Person(
    val id: String,
    val name: String,
    val photoUrl: String = "https://vieraboschkova.github.io/swapi-gallery/static/assets/img/people/$id.jpg",
    val streamUrl: String = "https://stream-akamai.castr.com/5b9352dbda7b8c769937e459/live_2361c920455111ea85db6911fe397b9e/index.fmp4.m3u8",
)