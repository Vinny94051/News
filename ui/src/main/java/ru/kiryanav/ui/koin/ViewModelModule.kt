package ru.kiryanav.ui.koin

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kiryanav.ui.presentation.viewmodel.NewsViewModel

val viewModelModule = module {
    viewModel {
        NewsViewModel(get(), get())
    }
}