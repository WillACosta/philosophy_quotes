package com.example.philosophyquotes.data.repository.remote.quote_remote_repository

import com.example.philosophyquotes.data.listener.ApiListener
import com.example.philosophyquotes.data.model.Quote

interface QuoteRemoteRepository {
    fun getRandomQuote(listener: ApiListener<Quote>)
}