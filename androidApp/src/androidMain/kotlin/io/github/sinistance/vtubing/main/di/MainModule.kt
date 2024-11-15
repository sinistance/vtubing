package io.github.sinistance.vtubing.main.di

import io.github.sinistance.vtubing.main.presentation.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel() }
}