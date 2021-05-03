package com.kiryanav.domain

import com.kiryanav.domain.error.NewsError
import com.kiryanav.domain.error.SourceError
import com.kiryanav.domain.model.*
import vlnny.base.data.model.ResponseResult
import java.util.*

interface NewsInteractor {

    /**
     * Get all news by
     * @param query like keyword in period
     * @param from date
     * @param to date
     * @param language - searching language
     */

    suspend fun getNews(
        query: String? = null,
        from: Date? = null,
        to: Date? = null,
        sources: List<ArticleSource>,
        language: String? = null,
        pageNumber: Int = 1
    ): ResponseResult<NewsWrapper, NewsError>

    suspend fun saveArticle(article: Article): ResponseResult<Unit, NewsError>

    suspend fun getSavedArticles(): ResponseResult<List<SavedArticleWrapper>, NewsError>

    suspend fun getSourcesByLanguage(language: String): ResponseResult<List<ArticleSource>, SourceError>

    suspend fun getSavedSources(): ResponseResult<List<ArticleSource>, SourceError>

    suspend fun saveSources(sources: List<ArticleSource>): ResponseResult<Unit, SourceError>

    suspend fun deleteSource(source: ArticleSource): ResponseResult<Unit, SourceError>

    suspend fun getSources(): ResponseResult<List<SavedArticleSourceWrapper>, SourceError>

    suspend fun deleteArticle(article: Article): ResponseResult<Unit, NewsError>
}
