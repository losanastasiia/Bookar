package com.onpu.bookar.api

import com.onpu.bookar.model.BookModel
import com.onpu.bookar.model.BookWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {

    @GET("volumes")
    fun getBook(
        @Query("q") title: String,
        @Query("key") key: String = "AIzaSyBR1kAEk2qbAlCHYBJPIDmtOrwplQqCcqg"
    ): BookWrapper
}