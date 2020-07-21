package ru.kiryanav.news.dagger

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.kiryanav.news.data.database.AppDatabase
import ru.kiryanav.news.data.database.dao.ArticleDao
import javax.inject.Singleton


@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.ARTICLES_TABLE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideArticlesDao(database: AppDatabase): ArticleDao = database.articlesDao()

}