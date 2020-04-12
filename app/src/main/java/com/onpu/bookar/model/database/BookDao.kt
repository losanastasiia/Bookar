package com.onpu.bookar.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {

    @Insert
    fun insertBook(book: Book)

    @Query("DELETE FROM Book WHERE bookId=:bookId")
    fun deleteBook(bookId: String)

    @Query("SELECT * FROM Book WHERE bookId=:id")
    fun findBookById(id: String): Book?
}