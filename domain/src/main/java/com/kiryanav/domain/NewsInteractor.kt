package com.kiryanav.domain

import com.kiryanav.domain.model.*
import vlnny.base.data.model.ResponseResult

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
        from: String? = null,
        to: String? = null,
        sources: List<ArticleSource>,
        language: String? = null,
        pageNumber: Int = 1
    ): ResponseResult<NewsWrapper, Error>

    suspend fun saveArticle(article: Article): ResponseResult<Unit, Error>

    suspend fun getSavedArticles(): ResponseResult<List<SavedArticleWrapper>, Error>

    suspend fun getSourcesByLanguage(language: String): ResponseResult<List<ArticleSource>, Error>

    suspend fun getSavedSources(): ResponseResult<List<ArticleSource>, Error>

    suspend fun saveSources(sources: List<ArticleSource>): ResponseResult<Unit, Error>

    suspend fun deleteSource(source: ArticleSource): ResponseResult<Unit, Error>

    suspend fun getSources(): ResponseResult<List<SavedArticleSourceWrapper>, Error>

    suspend fun deleteArticle(article: Article): ResponseResult<Unit, Error>
}
