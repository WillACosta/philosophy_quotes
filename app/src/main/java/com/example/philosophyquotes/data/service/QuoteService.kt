package com.example.philosophyquotes.data.service

import com.example.philosophyquotes.data.model.Quote
import retrofit2.Call
import retrofit2.http.GET

interface QuoteService {

    @GET("random")
    fun getRandomQuote(): Call<Quote>
}