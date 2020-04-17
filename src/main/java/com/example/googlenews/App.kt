package com.example.googlenews

import android.app.Application
import com.example.googlenews.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        private lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@App)
            modules(appModules)
        }
    }

    fun getInstance(): App {
        if (instance == null) {
            instance = App()
        }
        return instance
    }
}