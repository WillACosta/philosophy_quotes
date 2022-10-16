package com.example.philosophyquotes.data.service

import com.example.philosophyquotes.data.model.Quote
import retrofit2.Response
import retrofit2.http.GET

interface QuoteService {

    @GET("random")
    suspend fun getRandomQuote(): Response<Quote>
}