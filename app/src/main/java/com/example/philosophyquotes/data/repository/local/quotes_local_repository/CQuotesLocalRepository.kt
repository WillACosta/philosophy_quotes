package com.example.philosophyquotes.data.repository.local.quotes_local_repository

import com.example.philosophyquotes.data.data_source.QuotesDAO
import com.example.philosophyquotes.data.model.Quote

class CQuotesLocalRepository(private val dataSource: QuotesDAO) : QuotesLocalRepository {
    override fun save(quote: Quote): Boolean {
        return dataSource.save(quote) > 0
    }

    override fun delete(id: Int) {
        dataSource.delete(id)
    }

    override fun getAll(): List<Quote> {
        return dataSource.getAll()
    }

    override fun getQuoteByID(id: Int): Quote? {
        return dataSource.getByID(id)
    }
}