package io.github.sinistance.vtubing.mypage.di

import io.github.sinistance.vtubing.mypage.presentation.MyPageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val myPageModule = module {
    viewModel { MyPageViewModel() }
}