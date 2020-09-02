package com.kiryanav.domain.model

enum class SortBy(val keyword : String) {
    RELEVANCY("relevancy"),
    POPULARITY("popularity"),
    PUBLISHED_AT("publishedAt"),
}