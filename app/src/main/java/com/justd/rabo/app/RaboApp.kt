package com.justd.rabo.app

import android.app.Application
import com.justd.rabo.BuildConfig
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger
import org.koin.log.EmptyLogger

class RaboApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(
            this,
            modules,
            logger = if (BuildConfig.DEBUG) AndroidLogger(false) else EmptyLogger()
        )
    }
}