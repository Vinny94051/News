package ru.kiryanov.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ru.kiryanov.database.converter.Converter
import ru.kiryanov.database.dao.ArticleDao
import ru.kiryanov.database.dao.ArticleSourceDao
import ru.kiryanov.database.entites.ArticleEntity
import ru.kiryanov.database.entites.ArticleSourceEntity

@Database(
    entities = [
        ArticleEntity::class, ArticleSourceEntity::class
    ],
    version = AppDatabase.VERSION
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val VERSION = 2
        const val ARTICLES_TABLE_NAME = "articles"
    }

    abstract fun articleDao(): ArticleDao

    abstract fun sourceDao(): ArticleSourceDao


}