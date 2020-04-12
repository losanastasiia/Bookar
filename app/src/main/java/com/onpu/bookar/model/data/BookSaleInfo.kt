package com.onpu.bookar.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class BookSaleInfo(
    @SerializedName("buyLink")val buyLink: String,
    @SerializedName("listPrice")val listPrice: BookListPrice
)