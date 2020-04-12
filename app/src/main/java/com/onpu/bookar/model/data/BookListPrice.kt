package com.onpu.bookar.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class BookListPrice(
    val amount: Float,
    val currencyCode: String
)