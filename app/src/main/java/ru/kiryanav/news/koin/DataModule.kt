package ru.kiryanav.news.koin

import androidx.room.Room
import org.koin.dsl.module
import ru.kiryanav.news.data.database.AppDatabase
import ru.kiryanav.news.data.network.RetrofitClient

val dataModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            AppDatabase.ARTICLES_TABLE_NAME
        )
            .build()
    }
    single {
        get<AppDatabase>().articleDao()
    }

    single { RetrofitClient.getNewsApi() }
}