package com.onpu.bookar.model.data

import com.google.gson.annotations.SerializedName

// обычные дата классы, которые содержат в себе всю информацию о книге,
// которую мы получаем из Google Book Api
data class BookDetail(
    val title: String,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val categories: List<String>?,
    @SerializedName("imageLinks") val images: BookImages?,
    val language: String?,
    val previewLink: String?
)