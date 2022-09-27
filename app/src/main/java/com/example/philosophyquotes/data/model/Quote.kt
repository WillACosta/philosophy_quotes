package com.example.philosophyquotes.data.model

import com.google.gson.annotations.SerializedName

class Quote {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("body")
    lateinit var quote: String

    @SerializedName("author_id")
    var authorId: Int = 0

    @SerializedName("author")
    lateinit var author: String
}
