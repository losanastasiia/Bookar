package com.onpu.bookar.model.data

import com.google.gson.annotations.SerializedName
import com.onpu.bookar.model.data.BookModel

data class BookWrapper(
    @SerializedName("items")var books: List<BookModel>?
)