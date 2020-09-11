package ru.kiryanav.data.koin

import androidx.room.Room
import androidx.room.migration.Migration
import com.kiryanav.domain.repoApi.LocalNewsRepository
import com.kiryanav.domain.repoApi.RemoteNewsRepository
import org.koin.dsl.module
import ru.kiryanav.data.database.AppDatabase
import ru.kiryanav.data.network.RetrofitClient
import ru.kiryanav.data.repository.LocalArticleRepository
import ru.kiryanav.data.repository.RemoteArticleRepository

val dataModule = module {
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

    single { RetrofitClient.getNewsApi() }

    single<RemoteNewsRepository> {
        RemoteArticleRepository(get())
    }

    single<LocalNewsRepository> {
        LocalArticleRepository(get(), get())
    }

}
