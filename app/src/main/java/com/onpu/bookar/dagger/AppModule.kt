package com.onpu.bookar.dagger

import android.content.Context
import com.onpu.bookar.api.Service
import com.onpu.bookar.model.database.BookDatabase
import com.onpu.bookar.model.repo.DataRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context) =
        BookDatabase.instance(context)

    @Provides
    @Singleton
    fun provideDao(bookDatabase: BookDatabase) =
        bookDatabase.getBookDao()

    @Provides
    @Singleton
    fun provideService() = Service.getInstance()

    @Provides
    @Singleton
    fun provideNetworkRepo(service: Service) = DataRepository(service)
}