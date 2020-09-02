package ru.kiryanav.data.dto.news.request

import com.kiryanav.domain.model.SortBy


data class NewsRequest(
    val query: String,
    val from: String,
    val to: String,
    val language: String,
    val sortBy: SortBy
)