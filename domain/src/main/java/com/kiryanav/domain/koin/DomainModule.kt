package com.kiryanav.domain.koin

import com.kiryanav.domain.NewsInteractor
import com.kiryanav.domain.NewsInteractorImpl
import com.kiryanav.domain.worker.NewsUpdater
import com.kiryanav.domain.worker.NewsUpdaterListener
import com.kiryanav.domain.worker.NewsUpdaterReceiver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module


@ExperimentalCoroutinesApi
val domainModule = module {
    single<NewsInteractor> { NewsInteractorImpl(get(), get(),get()) }

    single {
        NewsUpdater()
    }

    single<NewsUpdaterReceiver> {
        get<NewsUpdater>()
    }

    single<NewsUpdaterListener> {
        get<NewsUpdater>()
    }
}