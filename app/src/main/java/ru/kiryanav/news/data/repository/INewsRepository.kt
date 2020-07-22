package ru.kiryanav.news.data.repository

import io.reactivex.Single
import ru.kiryanav.news.domain.model.NewsUIModel
import ru.kiryanav.news.domain.model.SortBy

interface INewsRepository {
    fun getEverything(
        query: String,
        from: String,
        to: String,
        language: String,
        dayNumber : Int,
        pageNumber : Int = 1,
        sortBy: SortBy = SortBy.PUBLISHED_AT
    ): Single<NewsUIModel>
}