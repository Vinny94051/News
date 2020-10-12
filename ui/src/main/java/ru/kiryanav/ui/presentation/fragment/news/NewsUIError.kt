package ru.kiryanav.ui.presentation.fragment.news

sealed class NewsUIError {
    object Unknown : NewsUIError()
    object BadApiKey : NewsUIError()
    object NoSavedSources : NewsUIError()
    object SavingError : NewsUIError()
    object RemovingError : NewsUIError()
}