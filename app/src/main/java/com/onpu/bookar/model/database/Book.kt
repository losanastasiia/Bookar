package com.onpu.bookar.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// класс, который представляет таблицу нашей базы данных
@Entity
data class Book(
    @PrimaryKey(autoGenerate = false)
    val bookId: String,
    val image: String?,
    val title: String
)