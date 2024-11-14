package io.github.sinistance.vtubing.login.di

import io.github.sinistance.vtubing.login.presentation.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel() }
}