package io.github.sinistance.vtubing.broadcast.di

import io.github.sinistance.vtubing.broadcast.presentation.BroadcastViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val broadcastModule = module {
    viewModel { BroadcastViewModel() }
}