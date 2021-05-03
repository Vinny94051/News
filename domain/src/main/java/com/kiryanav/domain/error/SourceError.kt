package com.kiryanav.domain.error

sealed class SourceError : Error {
    object  Unknown : SourceError()
    object BadApiKey : SourceError()
}