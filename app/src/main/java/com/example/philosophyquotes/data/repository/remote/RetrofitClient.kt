package com.example.philosophyquotes.data.repository.remote

import com.example.philosophyquotes.constants.AppConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {
        private lateinit var retrofit: Retrofit

        private fun instance(): Retrofit {
            val httpClient = OkHttpClient.Builder().build()

            if (!::retrofit.isInitialized) {
                synchronized(Retrofit::class) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(AppConstants.API.BASE_URL)
                        .client(httpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }

            return retrofit
        }

        fun <T> getService(service: Class<T>): T {
            return instance().create(service)
        }
    }
}