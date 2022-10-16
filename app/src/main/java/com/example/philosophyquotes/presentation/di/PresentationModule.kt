package com.example.philosophyquotes.presentation.di

import com.example.philosophyquotes.presentation.fragments.HomeFragment
import com.example.philosophyquotes.presentation.fragments.MyQuotesFragment
import com.example.philosophyquotes.presentation.viewmodel.HomeViewModel
import com.example.philosophyquotes.presentation.viewmodel.MyQuotesViewModel
import com.example.philosophyquotes.presentation.viewmodel.NameViewModel
import com.example.philosophyquotes.presentation.viewmodel.WelcomeViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

// Uses the PresentationModule as a static access with object definition
object PresentationModule {

    fun load() {
        loadKoinModules(viewModelModule() + fragmentsModule())
    }

    private fun viewModelModule(): Module {
        return module {
            factory { WelcomeViewModel(get()) }
            factory { HomeViewModel(get(), get()) }
            factory { NameViewModel(get()) }
            factory { MyQuotesViewModel(get()) }
        }
    }

    private fun fragmentsModule(): Module {
        return module {
            fragment { HomeFragment() }
            fragment { MyQuotesFragment() }
        }
    }

}