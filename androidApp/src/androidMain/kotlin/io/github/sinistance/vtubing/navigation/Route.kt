package io.github.sinistance.vtubing.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    val id: Int
        get() = hashCode()

    @Serializable
    data object Login : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object MyPage : Route

    @Serializable
    data object Broadcast : Route

    @Serializable
    data object Stream : Route

    @Serializable
    data object Setting : Route
}