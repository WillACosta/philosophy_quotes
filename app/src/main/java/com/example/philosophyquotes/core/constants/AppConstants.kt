package com.example.philosophyquotes.core.constants

class AppConstants private constructor() {
    object SHARED {
        const val PREFERENCES_KEY = "@app_prefs"
        const val USER_NAME_KEY = "name"
        const val IS_FIRST_ACCESS_KEY = "first_access"
    }

    object API {
        const val BASE_URL = "https://stoicquotesapi.com/v1/api/quotes/"
    }

    object HTTP {
        const val SUCCESS = 200
    }

    object DATA_SOURCE {
        const val TABLE_NAME = "QUOTE"
    }

    object LOG {
        const val OK_HTTP = "OK_HTTP"
    }
}