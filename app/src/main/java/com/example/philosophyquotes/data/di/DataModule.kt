package com.example.philosophyquotes.data.di

import android.util.Log
import com.example.philosophyquotes.core.constants.AppConstants
import com.example.philosophyquotes.data.repository.local.app_preferences.AppPreferences
import com.example.philosophyquotes.data.repository.local.app_preferences.CAppPreferences
import com.example.philosophyquotes.data.repository.local.quotes_local_repository.CQuotesLocalRepository
import com.example.philosophyquotes.data.repository.local.quotes_local_repository.QuotesLocalRepository
import com.example.philosophyquotes.data.repository.remote.quote_remote_repository.CQuoteRemoteRepository
import com.example.philosophyquotes.data.repository.remote.quote_remote_repository.QuoteRemoteRepository
import com.example.philosophyquotes.data.service.QuoteService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    fun load() {
        loadKoinModules(
            androidContextModule()
                    + appPreferencesModule()
                    + repositoryModule()
                    + networkModule()
        )
    }

    private fun androidContextModule(): Module {
        return module {
            factory { androidApplication().applicationContext }
        }
    }

    private fun appPreferencesModule(): Module {
        return module {
            factory<AppPreferences> { CAppPreferences(get()) }
        }
    }

    private fun repositoryModule(): Module {
        return module {
            factory<QuoteRemoteRepository> { CQuoteRemoteRepository(get(), get()) }
            factory<QuotesLocalRepository> { CQuotesLocalRepository(get()) }
        }
    }

    private fun networkModule(): Module {
        return module {
            single { createService<QuoteService>(get(), get()) }

            // create a instance for any JSON converter type | dependency inversion
            single { GsonConverterFactory.create() }

            single { createHtppClient() }
        }
    }

    // refield keyword is used by indicates an implicit class type for usage
    private inline fun <reified T> createService(
        factory: GsonConverterFactory,
        client: OkHttpClient
    ): T {
        return Retrofit.Builder()
            .baseUrl(AppConstants.API.BASE_URL)
            .addConverterFactory(factory)
            .client(client)
            .build()
            .create(T::class.java)
    }

    private fun createHtppClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor {
            Log.e(AppConstants.LOG.OK_HTTP, it)
        }

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

}