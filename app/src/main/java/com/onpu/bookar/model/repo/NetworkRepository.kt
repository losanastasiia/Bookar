package com.onpu.bookar.model.repo

import com.onpu.bookar.api.Service
import com.onpu.bookar.model.data.BookWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRepository {

    private val service = Service.getInstance()

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

    interface RequestCallback {
        fun onRequestSuccess(book: BookWrapper?)
        fun onRequestFailed()
    }
}