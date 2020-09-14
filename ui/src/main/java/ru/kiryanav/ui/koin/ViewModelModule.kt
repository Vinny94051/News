package ru.kiryanav.ui.koin

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kiryanav.ui.presentation.viewmodel.NewsViewModel
import ru.kiryanav.ui.presentation.viewmodel.SavedNewsViewModel
import ru.kiryanav.ui.presentation.viewmodel.SettingsViewModel

val viewModelModule = module {
    viewModel {
        NewsViewModel(get(), get())
    }

    viewModel {
        SettingsViewModel(get())
    }

    viewModel {
        SavedNewsViewModel(get(), get())
    }
}