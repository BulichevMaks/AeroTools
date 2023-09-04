package com.formgrav.aerotools

import android.app.Application
import com.formgrav.aerotools.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class App: Application() {


    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidContext(this@App)
            modules(
                mainModule
            )
        }
    }
}