package com.example.philosophyquotes.data.repository.local.quotes_local_repository

import com.example.philosophyquotes.data.model.Quote

interface QuotesLocalRepository {
    fun save(quote: Quote): Boolean
    fun delete(id: Int)
    fun getAll(): List<Quote>
    fun getQuoteByID(id: Int): Quote?
}