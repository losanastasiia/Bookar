package com.onpu.bookar.model.data

import com.google.gson.annotations.SerializedName

data class BookModel(
    @SerializedName("id") val id: String,
    @SerializedName("volumeInfo")val details: BookDetail,
    @SerializedName("saleInfo")val saleInfo: BookSaleInfo,
    var saved: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}