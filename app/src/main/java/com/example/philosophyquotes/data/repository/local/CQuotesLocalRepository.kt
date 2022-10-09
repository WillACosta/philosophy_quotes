package com.example.philosophyquotes.data.repository.local

import android.content.Context
import com.example.philosophyquotes.data.data_source.QuotesLocalDataSource
import com.example.philosophyquotes.data.model.Quote

class CQuotesLocalRepository(context: Context) : QuotesLocalRepository {
    private val dataSource = QuotesLocalDataSource.instance(context).quotesDAO()

    override fun save(quote: Quote): Boolean {
        return dataSource.save(quote) > 0
    }

    override fun delete(id: Int) {
        val quoteToDelete = dataSource.getByID(id)
        dataSource.delete(quoteToDelete)
    }

    override fun getAll(): List<Quote> {
        return dataSource.getAll()
    }
}