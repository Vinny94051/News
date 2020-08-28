package ru.kiryanav.news.data.repository

import ru.kiryanav.news.domain.model.NewsUIModel
import ru.kiryanav.news.domain.model.SortBy

interface INewsRepository {
    suspend fun getEverything(
        query: String,
        from: String,
        to: String,
        language: String,
        dayNumber: Int,
        pageNumber: Int = 1,
        sortBy: SortBy = SortBy.PUBLISHED_AT
    ): NewsUIModel
}