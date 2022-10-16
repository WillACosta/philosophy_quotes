package com.example.philosophyquotes.data.repository.remote.quote_remote_repository

import android.content.Context
import com.example.philosophyquotes.R
import com.example.philosophyquotes.core.exceptions.RemoteException
import com.example.philosophyquotes.data.model.Quote
import com.example.philosophyquotes.data.service.QuoteService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CQuoteRemoteRepository(
    context: Context,
    private val service: QuoteService
) : QuoteRemoteRepository {

    private val message = context.getString(R.string.UNEXPECTED_ERROR)

    override suspend fun getRandomQuote(): Flow<Quote> = flow {
        try {
            val response = service.getRandomQuote()

            if (response.isSuccessful) {
                emit(response.body()!!)
            }

        } catch (ex: HttpException) {
            throw RemoteException(ex.message ?: message)
        }
    }

}