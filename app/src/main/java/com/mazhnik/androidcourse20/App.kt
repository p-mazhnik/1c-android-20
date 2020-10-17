/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20

import android.app.Application
import com.mazhnik.androidcourse20.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, databaseModule, repositoryModule, viewModelModule))
        }
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
    }
}

