package io.github.sinistance.vtubing.di

import io.github.sinistance.vtubing.network.di.networkModule
import io.github.sinistance.vtubing.people.di.sharedPeopleModule

val sharedModule = listOf(
    networkModule,
    sharedPeopleModule,
)