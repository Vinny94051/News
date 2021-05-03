package ru.kiryanav.data.dto.news.response

enum class SortByForApi(val keyword : String) {
    RELEVANCY("relevancy"),
    POPULARITY("popularity"),
    PUBLISHED_AT("publishedAt"),
}