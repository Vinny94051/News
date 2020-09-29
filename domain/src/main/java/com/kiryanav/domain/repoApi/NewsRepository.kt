package com.kiryanav.domain.repoApi

import com.kiryanav.domain.Error
import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy
import com.kiryanav.domain.model.ArticleSource
import vlnny.base.data.model.ResponseResult

interface NewsRepository {
    suspend fun getNews(
        query: String?,
        from: String?,
        to: String?,
        sources : List<ArticleSource>,
        language: String?,
        pageNumber: Int,
        sortBy: SortBy = SortBy.PUBLISHED_AT
    ): ResponseResult<News, Error>

    suspend fun getSourcesByLanguage(
        language: String
    ): ResponseResult<List<ArticleSource>,Error>

}