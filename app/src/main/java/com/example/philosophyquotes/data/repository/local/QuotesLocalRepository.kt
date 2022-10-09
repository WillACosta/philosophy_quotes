package com.example.philosophyquotes.data.repository.local

import com.example.philosophyquotes.data.model.Quote

interface QuotesLocalRepository {
    fun save(quote: Quote): Boolean
    fun delete(id: Int)
    fun getAll(): List<Quote>
}