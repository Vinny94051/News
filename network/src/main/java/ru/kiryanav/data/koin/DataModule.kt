package ru.kiryanav.data.koin

import com.kiryanav.domain.repoApi.NewsRepository
import org.koin.dsl.module
import ru.kiryanav.data.network.RetrofitClient
import ru.kiryanav.data.repository.NewsRepositoryImpl

val dataModule = module {
    single { RetrofitClient.getNewsApi() }

    single<NewsRepository> {
        NewsRepositoryImpl(get())
    }
}
