package io.github.sinistance.vtubing.di

import io.github.sinistance.vtubing.home.di.homeModule
import io.github.sinistance.vtubing.login.di.loginModule
import io.github.sinistance.vtubing.main.di.mainModule
import io.github.sinistance.vtubing.mypage.di.myPageModule
import io.github.sinistance.vtubing.stream.di.streamModule

val appModule = listOf(
    mainModule,
    loginModule,
    homeModule,
    myPageModule,
    streamModule,
)