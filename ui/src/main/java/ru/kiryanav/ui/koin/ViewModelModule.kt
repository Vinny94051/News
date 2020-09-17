package ru.kiryanav.ui.koin

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kiryanav.ui.presentation.fragment.news.current.NewsViewModel
import ru.kiryanav.ui.presentation.fragment.news.selected.SelectedNewsViewModel
import ru.kiryanav.ui.presentation.fragment.settings.SettingsViewModel

val viewModelModule = module {
    viewModel {
        NewsViewModel(
            get(),
            get()
        )
    }

    viewModel {
        SettingsViewModel(get())
    }

    viewModel {
        SelectedNewsViewModel(
            get(),
            get()
        )
    }
}