package com.onpu.bookar.model.repo

import com.onpu.bookar.api.Service
import com.onpu.bookar.model.data.BookModel
import com.onpu.bookar.model.data.BookWrapper
import com.onpu.bookar.model.database.Book
import com.onpu.bookar.model.database.BookDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// класс, входящий в слой "репозиторий", который отвечает за получение данных из сети, бд и управление ими
class DataRepository(val service: Service, val bookDao: BookDao) {

    // "лезем" в сеть (Google Book Api) для получения данных о книге по названию
    fun findBook(title: String, callback: RequestCallback) {
        service.getBookApi().getBook(title).enqueue(
            object :Callback<BookWrapper> {
                override fun onFailure(call: Call<BookWrapper>, t: Throwable) {
                    t.printStackTrace()
                    callback.onRequestFailed()
                }

                override fun onResponse(call: Call<BookWrapper>, response: Response<BookWrapper>) {
                    callback.onRequestSuccess(response.body())
                }
            }
        )
    }

    // отправляем запрос в наш АПИ для получения информации о книге по её уникальному id
    fun findBookById(id: String, callback: RequestCallback) {
        service.getBookApi().getBookById(id).enqueue(
            object :Callback<BookModel> {
                override fun onFailure(call: Call<BookModel>, t: Throwable) {
                    t.printStackTrace()
                    callback.onRequestFailed()
                }

                override fun onResponse(call: Call<BookModel>, response: Response<BookModel>) {
                    callback.onRequestSuccess(BookWrapper(response.body()?.let {listOf(it)}?: emptyList()))
                }
            }
        )
    }

    fun getBooksId() = bookDao.getBooksId()

    fun getBooks() = bookDao.getBooks()

    fun deleteBook(bookId: String) {
        bookDao.deleteBook(bookId)
    }

    fun saveBook(book: Book) {
        bookDao.insertBook(book)
    }

    interface RequestCallback {
        fun onRequestSuccess(book: BookWrapper?)
        fun onRequestFailed()
    }
}