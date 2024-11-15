package io.github.sinistance.vtubing.di

import io.github.sinistance.vtubing.home.di.homeModule
import io.github.sinistance.vtubing.login.di.loginModule

val appModule = listOf(
    loginModule,
    homeModule,
)