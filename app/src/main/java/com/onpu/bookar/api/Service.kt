package com.onpu.bookar.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Service {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getBookApi(): GoogleBooksApi {
        return retrofit.create(GoogleBooksApi::class.java)
    }

    companion object {
        private lateinit var service: Service

        fun getInstance(): Service {
            if(this::service.isInitialized.not()) {
                service = Service()
            }
            return service
        }
    }
}