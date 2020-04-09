package com.onpu.bookar.model

data class BookDetail(
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val categories: List<String>,
    val images: BookImages,
    val language: String,
    val previewLink: String
)