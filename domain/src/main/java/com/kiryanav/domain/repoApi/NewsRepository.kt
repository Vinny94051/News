package com.kiryanav.domain.repoApi

import com.kiryanav.domain.error.NewsError
import com.kiryanav.domain.error.SourceError
import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy
import com.kiryanav.domain.model.ArticleSource
import vlnny.base.data.model.ResponseResult
import java.util.*

interface NewsRepository {
    suspend fun getNews(
        query: String?,
        from: Date?,
        to: Date?,
        sources: List<ArticleSource>,
        language: String?,
        pageNumber: Int,
        sortBy: SortBy = SortBy.PUBLISHED_AT
    ): ResponseResult<News, NewsError>

    suspend fun getSources(
        language: String = Locale.getDefault().language
    ): ResponseResult<List<ArticleSource>, SourceError>

}