package com.kiryanav.domain.error

sealed class NewsError : Error {
    object Unknown : NewsError()
    object NoSavedSources : NewsError()
    object BadApiKey : NewsError()
}