package com.onpu.bookar.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// класс ответственный за создание ретрофит клиента
class Service {

    // создаём ретрофит клиент
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // получаем GoogleBook Api, который мы будем использовать для отправления запросов
    fun getBookApi(): GoogleBooksApi {
        return retrofit.create(GoogleBooksApi::class.java)
    }

    companion object {
        private lateinit var service: Service

        // получаем экземпляр класса Service, как синглтона
        fun getInstance(): Service {
            if(this::service.isInitialized.not()) {
                service = Service()
            }
            return service
        }
    }
}