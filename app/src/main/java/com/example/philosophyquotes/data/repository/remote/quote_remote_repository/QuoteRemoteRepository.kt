package com.example.philosophyquotes.data.repository.remote.quote_remote_repository

import com.example.philosophyquotes.data.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRemoteRepository {
    suspend fun getRandomQuote(): Flow<Quote>
}