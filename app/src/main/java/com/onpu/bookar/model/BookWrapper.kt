package com.onpu.bookar.model

import com.google.gson.annotations.SerializedName

data class BookWrapper(
    @SerializedName("items")val books: List<BookModel>
)