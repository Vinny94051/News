package ru.kiryanav.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kiryanav.data.database.dao.ArticleDao
import ru.kiryanav.data.database.entites.ArticleEntity

@Database(
    entities = [
        ArticleEntity::class
    ],
    version = AppDatabase.VERSION
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val VERSION = 1
        const val ARTICLES_TABLE_NAME = "articles"
    }

    abstract fun articleDao(): ArticleDao

}