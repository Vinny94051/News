package com.kiryanav.domain.koin

import com.kiryanav.domain.NewsInteractor
import com.kiryanav.domain.NewsInteractorImpl
import org.koin.dsl.module


val domainModule = module {
    single<NewsInteractor> { NewsInteractorImpl(get(), get(),get()) }
}