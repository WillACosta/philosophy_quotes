package com.example.philosophyquotes.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.philosophyquotes.data.model.Quote

@Dao
interface QuotesDAO {

    @Insert
    fun save(quote: Quote): Long

    @Query("DELETE from QUOTE WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM QUOTE")
    fun getAll(): List<Quote>

    @Query("SELECT * FROM QUOTE WHERE id = :id")
    fun getByID(id: Int): Quote?
}