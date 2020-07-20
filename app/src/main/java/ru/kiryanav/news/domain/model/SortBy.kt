package ru.kiryanav.news.domain.model

enum class SortBy(val keyword : String) {
    RELEVANCY("relevancy"),
    POPULARITY("popularity"),
    PUBLISHED_AT("publishedAt"),
}