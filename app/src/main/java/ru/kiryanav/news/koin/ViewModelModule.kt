package ru.kiryanav.news.koin

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kiryanav.news.presentation.viewmodel.NewsViewModel

val viewModelModule = module {
    viewModel {
        NewsViewModel(get(), get())
    }
}