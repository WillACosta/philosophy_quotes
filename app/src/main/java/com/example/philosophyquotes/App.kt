package com.example.philosophyquotes

import android.app.Application
import com.example.philosophyquotes.data.di.DataModule
import com.example.philosophyquotes.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            fragmentFactory()
        }

        PresentationModule.load()
        DataModule.load()
    }

}