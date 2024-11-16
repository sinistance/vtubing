package io.github.sinistance.vtubing.home.di

import io.github.sinistance.vtubing.home.presentation.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel(get()) }
}