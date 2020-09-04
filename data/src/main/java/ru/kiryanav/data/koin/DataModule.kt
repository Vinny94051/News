package ru.kiryanav.data.koin

import androidx.room.Room
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
        )
            .build()
    }
    single {
        get<AppDatabase>().articleDao()
    }

    single { RetrofitClient.getNewsApi() }

    single<RemoteNewsRepository> {
        RemoteArticleRepository(get(),get())
    }

    single<LocalNewsRepository> {
        LocalArticleRepository(get())
    }

}
