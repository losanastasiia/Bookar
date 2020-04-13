package com.onpu.bookar.api

import com.onpu.bookar.model.data.BookModel
import com.onpu.bookar.model.data.BookWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// отправляем запросы в гугл бук апи
interface GoogleBooksApi {

    // поиск книги по названию
    @GET("volumes")
    fun getBook(
        @Query("q") title: String,
        @Query("key") key: String = "AIzaSyBR1kAEk2qbAlCHYBJPIDmtOrwplQqCcqg"
    ): Call<BookWrapper>

    // получение информации о книге по её id
    @GET("volumes/{volumeId}")
    fun getBookById(
        @Path("volumeId") id: String,
        @Query("key") key: String = "AIzaSyBR1kAEk2qbAlCHYBJPIDmtOrwplQqCcqg"
    ): Call<BookModel>
}