package com.example.philosophyquotes.utils

import android.content.Context
import android.content.Intent

class HelperFunctions {
    companion object {
        fun <T> startActivity(context: Context, className: Class<T>) {
            context.startActivity(Intent(context, className))
        }
    }
}