package com.example.philosophyquotes.data.repository.remote

import android.content.Context
import com.example.philosophyquotes.R
import com.example.philosophyquotes.constants.AppConstants
import com.example.philosophyquotes.data.listener.ApiListener
import com.example.philosophyquotes.data.model.Quote
import com.example.philosophyquotes.data.service.QuoteService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CQuoteRepository(private val context: Context) : QuoteRepository {
    private val service = RetrofitClient.getService(QuoteService::class.java)

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