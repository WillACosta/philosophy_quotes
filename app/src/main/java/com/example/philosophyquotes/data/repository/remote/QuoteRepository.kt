package com.example.philosophyquotes.data.repository.remote

import com.example.philosophyquotes.data.listener.ApiListener
import com.example.philosophyquotes.data.model.Quote

interface QuoteRepository {
    fun getRandomQuote(listener: ApiListener<Quote>)
}