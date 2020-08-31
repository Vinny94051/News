package ru.kiryanav.news.koin

import org.koin.dsl.module
import ru.kiryanav.news.data.repository.ArticleRepository
import ru.kiryanav.news.data.repository.IArticleRepository
import ru.kiryanav.news.data.repository.INewsRepository
import ru.kiryanav.news.data.repository.NewsRepository

val reposModule = module {
    single<INewsRepository> { NewsRepository(get(), get()) }
    single<IArticleRepository> { ArticleRepository(get()) }
}