package io.github.sinistance.vtubing.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    val id: Int
        get() = hashCode()

    @Serializable
    data object Login : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data object MyPage : Screen

    @Serializable
    data object Broadcast : Screen

    @Serializable
    data object Stream : Screen

    @Serializable
    data object Setting : Screen
}