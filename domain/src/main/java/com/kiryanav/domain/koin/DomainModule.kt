package com.kiryanav.domain.koin

import com.kiryanav.domain.INewsInteractor
import com.kiryanav.domain.NewsInteractor
import com.kiryanav.domain.prefs.PrefsManager
import com.kiryanav.domain.prefs.SourceManager
import org.koin.dsl.module


val domainModule = module {
    single<INewsInteractor> { NewsInteractor(get(), get()) }

    single<SourceManager> { PrefsManager(get()) }
}