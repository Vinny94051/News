package ru.kiryanav.data.koin

import com.kiryanav.domain.repoApi.IArticleRepository
import com.kiryanav.domain.repoApi.INewsRepository
import org.koin.dsl.module
import ru.kiryanav.data.repository.ArticleRepository
import ru.kiryanav.data.repository.NewsRepository

val repoModule = module {
    single<INewsRepository> {
        NewsRepository(get(), get())
    }

    single<IArticleRepository> {
        ArticleRepository(get())
    }
}