package com.onpu.bookar.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// класс представляющий нашу базу данных
@Database(
    version = 2,
    // перечисляем все таблицы нашей бд
    entities = [Book::class]
)
abstract class BookDatabase: RoomDatabase(){

    // метод, реализацию которого нам предоставит библиотека Room,
    // возвращаемый тип метода мы используем для отправления запросов в базу данных
    abstract fun getBookDao(): BookDao

    companion object {
        private val tableName = "Book"
        // получаем обЪект базы данных
        fun instance(context: Context) =
            Room.databaseBuilder(context, BookDatabase::class.java, tableName)
                .fallbackToDestructiveMigration()
                .build()
    }
}