package ru.kiryanav.ui.presentation.fragment.news.callback

interface NewsErrorCallback {
    fun noSavedSourcesError()
    fun unknownError()
    fun badApiKeyError()
}