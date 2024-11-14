package io.github.sinistance.vtubing.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(sharedModule)
    }
}