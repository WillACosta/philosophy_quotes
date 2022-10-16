package com.example.philosophyquotes.core.constants

class AppConstants private constructor() {
    object SHARED {
        const val PREFERENCES_KEY = "com.quotes.user_preferences"
        const val USER_NAME_KEY = "name"
        const val IS_FIRST_ACCESS_KEY = "first_access"
    }

    object API {
        const val BASE_URL = "https://stoicquotesapi.com/v1/api/quotes/"
    }

    object DATA_SOURCE {
        const val TABLE_NAME = "QUOTE"
    }

    object LOG {
        const val API_RESPONSE = "API_RESPONSE"
    }
}