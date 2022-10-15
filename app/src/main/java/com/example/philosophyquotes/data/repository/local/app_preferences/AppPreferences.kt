package com.example.philosophyquotes.data.repository.local.app_preferences

interface AppPreferences {
    fun <T> storeData(key: String, value: T)
    fun <T> getData(key: String, default: T): T?
    fun remove(key: String)
}