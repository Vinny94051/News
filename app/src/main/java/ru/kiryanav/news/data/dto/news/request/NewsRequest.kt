package ru.kiryanav.news.data.dto.news.request

import ru.kiryanav.news.domain.model.SortBy

data class NewsRequest(
    val query: String,
    val from: String,
    val to: String,
    val language: String,
    val sortBy: SortBy
)