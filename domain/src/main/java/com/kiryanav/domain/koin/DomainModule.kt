package com.kiryanav.domain.koin

import com.kiryanav.domain.INewsInteractor
import com.kiryanav.domain.NewsInteractor
import org.koin.dsl.module


val domainModule = module {
    single<INewsInteractor> { NewsInteractor(get(), get()) }
}