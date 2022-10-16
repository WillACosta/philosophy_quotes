package com.example.philosophyquotes.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.philosophyquotes.data.model.Quote

@Database(entities = [Quote::class], version = 1, exportSchema = false)
abstract class QuotesLocalDataSource : RoomDatabase() {

    abstract fun quotesDAO(): QuotesDAO
}