package com.example.philosophyquotes.data.repository.local.app_preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.philosophyquotes.core.constants.AppConstants

class CAppPreferences(context: Context) : AppPreferences {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.SHARED.PREFERENCES_KEY, Context.MODE_PRIVATE)

    override fun <T> storeData(key: String, value: T) {
        if (value is Boolean) {
            preferences.edit().putBoolean(key, value).apply()
        } else if (value is String) {
            preferences.edit().putString(key, value).apply()
        }
    }

    override fun <T> getData(key: String, default: T) : T {
        if (default is Boolean) {
            return preferences.getBoolean(key, default) as T
        } else if (default is String) {
            return preferences.getString(key, default) as T
        }

        return default
    }

    override fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }
}
