package io.github.sinistance.vtubing.stream.di

import io.github.sinistance.vtubing.stream.presentation.StreamViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val streamModule = module {
    viewModel { StreamViewModel() }
}