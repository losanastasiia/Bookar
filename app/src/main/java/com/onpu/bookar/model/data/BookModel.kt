package com.onpu.bookar.model.data

import com.google.gson.annotations.SerializedName

data class BookModel(
    val id: String,
    @SerializedName("volumeInfo")val details: BookDetail,
    val saleInfo: BookSaleInfo
)