package com.example.philosophyquotes.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.philosophyquotes.core.constants.AppConstants
import com.google.gson.annotations.SerializedName

@Entity(tableName = AppConstants.DATA_SOURCE.TABLE_NAME)
class Quote {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    @SerializedName("id")
    var id: Int = 0

    @ColumnInfo
    @SerializedName("body")
    var quote: String = ""

    @ColumnInfo
    @SerializedName("author_id")
    var authorId: Int = 0

    @ColumnInfo
    @SerializedName("author")
    var author: String = ""
}
