package io.github.sinistance.vtubing

import android.app.Application
import io.github.sinistance.vtubing.di.appModule
import io.github.sinistance.vtubing.di.sharedModule
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            modules(sharedModule + appModule)
        }
    }
}