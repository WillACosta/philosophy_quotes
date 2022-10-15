package com.example.philosophyquotes.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.philosophyquotes.core.constants.AppConstants
import com.example.philosophyquotes.data.model.Quote

@Database(entities = [Quote::class], version = 1)
abstract class QuotesLocalDataSource : RoomDatabase() {

    abstract fun quotesDAO(): QuotesDAO

    companion object {
        private lateinit var _instance: QuotesLocalDataSource

        fun instance(context: Context): QuotesLocalDataSource {
            if (!Companion::_instance.isInitialized) {

                synchronized(QuotesLocalDataSource::class) {
                    _instance = Room.databaseBuilder(
                        context,
                        QuotesLocalDataSource::class.java,
                        AppConstants.DATA_SOURCE.TABLE_NAME
                    )
                        .addMigrations()
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return _instance
        }
    }
}