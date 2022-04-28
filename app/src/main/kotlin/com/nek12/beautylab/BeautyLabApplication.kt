package com.nek12.beautylab

import android.app.Application
import com.nek12.beautylab.data.di.dataModule
import com.nek12.beautylab.data.di.networkModule
import com.nek12.beautylab.data.di.repoModule
import com.nek12.beautylab.di.appModule
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BeautyLabApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidLogcatLogger.installOnDebuggableApp(this, LogPriority.VERBOSE)

        startKoin {
            androidContext(applicationContext)
            modules(networkModule, dataModule, repoModule, appModule)
        }
    }
}
