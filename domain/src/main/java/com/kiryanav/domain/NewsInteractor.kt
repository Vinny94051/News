package com.kiryanav.domain

import com.kiryanav.domain.model.*

interface NewsInteractor {

    /**
     * Get all news by
     * @param query like keyword in period
     * @param from date
     * @param to date
     * @param language - searching language
     */

    suspend fun getNews(
        query: String?,
        from: String?,
        to: String?,
        sources : List<ArticleSource>,
        language: String?,
        pageNumber: Int = 1
    ): NewsWrapper

    /**
     * Save article in local storage
     * @param article - article which will be saved
     */

    suspend fun saveArticle(article: Article)

    /**
     * Get all saved articles
     */

    suspend fun getSavedArticles(): List<SavedArticleWrapper>

    suspend fun getSourcesByLanguage(language : String) : List<ArticleSource>

    suspend fun getSavedSources() : List<ArticleSource>

    suspend fun saveSources(sources : List<ArticleSource>)

    suspend fun deleteSource(source : ArticleSource)

    suspend fun getSources() : List<SavedArticleSourceWrapper>

    suspend fun deleteArticle(article: Article)
}
