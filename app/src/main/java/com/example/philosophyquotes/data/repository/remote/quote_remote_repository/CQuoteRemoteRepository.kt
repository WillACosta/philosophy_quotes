package com.example.philosophyquotes.data.repository.remote.quote_remote_repository

import android.content.Context
import com.example.philosophyquotes.R
import com.example.philosophyquotes.core.constants.AppConstants
import com.example.philosophyquotes.data.listener.ApiListener
import com.example.philosophyquotes.data.model.Quote
import com.example.philosophyquotes.data.service.QuoteService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CQuoteRemoteRepository(
    private val context: Context,
    private val service: QuoteService
) : QuoteRemoteRepository {

    override fun getRandomQuote(listener: ApiListener<Quote>) {
        service.getRandomQuote().enqueue(object : Callback<Quote> {
            override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                if (response.code() == AppConstants.HTTP.SUCCESS) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailure(getFailureMessage())
                }
            }

            override fun onFailure(call: Call<Quote>, t: Throwable) {
                listener.onFailure(getFailureMessage())
            }
        })
    }

    private fun getFailureMessage(): String {
        return context.getString(R.string.UNEXPECTED_ERROR)
    }

}