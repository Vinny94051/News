package ru.kiryanav.data.dto.news.request

import ru.kiryanav.data.dto.news.response.SortByForApi

data class NewsRequest(
    val query: String,
    val from: String,
    val to: String,
    val language: String,
    val sortBy: SortByForApi
)