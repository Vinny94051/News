package ru.kiryanov.database.koin

import androidx.room.Room
import com.kiryanav.domain.repoApi.ArticleRepository
import com.kiryanav.domain.repoApi.SourceRepository
import org.koin.dsl.module
import ru.kiryanov.database.AppDatabase
import ru.kiryanov.database.repos.ArticleRepositoryImpl
import ru.kiryanov.database.repos.SourceRepositoryImpl

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            AppDatabase.ARTICLES_TABLE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }
    single {
        get<AppDatabase>().articleDao()
    }

    single {
        get<AppDatabase>().sourceDao()
    }

    single<ArticleRepository> {
        ArticleRepositoryImpl(get())
    }

    single<SourceRepository> {
        SourceRepositoryImpl(get())
    }
}