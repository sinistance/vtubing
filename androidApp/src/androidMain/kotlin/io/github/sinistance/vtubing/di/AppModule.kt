package io.github.sinistance.vtubing.di

import io.github.sinistance.vtubing.login.di.loginModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(loginModule)
    }
}