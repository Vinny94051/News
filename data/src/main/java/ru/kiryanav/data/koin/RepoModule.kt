package ru.kiryanav.data.koin

import com.kiryanav.domain.repoApi.LocalRepository
import com.kiryanav.domain.repoApi.RemoteRepository
import org.koin.dsl.module
import ru.kiryanav.data.repository.LocalArticleRepository
import ru.kiryanav.data.repository.RemoteArticleRepository

val repoModule = module {
    single<RemoteRepository> {
        RemoteArticleRepository(get(), get())
    }

    single<LocalRepository> {
        LocalArticleRepository(get())
    }
}