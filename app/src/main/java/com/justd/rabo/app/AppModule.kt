package com.justd.rabo.app

import com.justd.rabo.app.networking.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module.module

object AppModule {

    val module = module {
        single {
            DispatcherProvider(Dispatchers.Main, Dispatchers.IO)
        }
    }
}