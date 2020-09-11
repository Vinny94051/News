package ru.kiryanav.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.kiryanav.data.database.dao.ArticleDao
import ru.kiryanav.data.database.dao.ArticleSourceDao
import ru.kiryanav.data.database.entites.ArticleEntity
import ru.kiryanav.data.database.entites.ArticleSourceEntity

@Database(
    entities = [
        ArticleEntity::class, ArticleSourceEntity::class
    ],
    version = AppDatabase.VERSION
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val VERSION = 2
        const val ARTICLES_TABLE_NAME = "articles"
//        val migration_1_2 = object : Migration(1,2){
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE sources")
//            }
//        }
    }

    abstract fun articleDao(): ArticleDao

    abstract fun sourceDao(): ArticleSourceDao



}