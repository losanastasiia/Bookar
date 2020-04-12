package com.onpu.bookar.model.data

import com.google.gson.annotations.SerializedName

data class BookSaleInfo(
    @SerializedName("buyLink") val buyLink: String?,
    @SerializedName("listPrice") val listPrice: BookListPrice?
)