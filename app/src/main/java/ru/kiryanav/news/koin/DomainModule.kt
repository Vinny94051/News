package ru.kiryanav.news.koin

import org.koin.dsl.module
import ru.kiryanav.news.domain.INewsInteractor
import ru.kiryanav.news.domain.NewsInteractor

val domainModule = module {
    single<INewsInteractor> { NewsInteractor(get(), get()) }
}