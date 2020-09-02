package com.kiryanav.domain.koin

import com.kiryanav.domain.NewsInteractor
import org.koin.dsl.module
import ru.kiryanav.ui.domainApi.INewsInteractor


val domainModule = module {
    single<INewsInteractor> { NewsInteractor(get(), get()) }
}