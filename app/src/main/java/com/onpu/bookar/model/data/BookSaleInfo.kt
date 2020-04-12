package com.onpu.bookar.model.data

import com.google.gson.annotations.SerializedName

data class BookSaleInfo(
    val buyLink: String,
    val listPrice: BookListPrice
)