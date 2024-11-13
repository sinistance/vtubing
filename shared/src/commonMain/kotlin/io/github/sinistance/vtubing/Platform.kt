package io.github.sinistance.vtubing

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform