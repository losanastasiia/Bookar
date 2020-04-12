package com.onpu.bookar.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [Book::class]
)
abstract class BookDatabase: RoomDatabase(){

    abstract fun getBookDao(): BookDao

    companion object {
        private val tableName = "Book"
        fun instance(context: Context) =
            Room.databaseBuilder(context, BookDatabase::class.java, tableName).build()
    }
}