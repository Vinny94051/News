package com.kiryanav.domain

import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.News

interface INewsInteractor {

    /**
     * Get all news by
     * @param query like keyword in period
     * @param from date
     * @param to date
     * @param language - searching language
     */

    suspend fun getNews(
        query: String,
        from: String,
        to: String,
        language: String,
        dayNumber: Int = Constants.ZERO_INT,
        pageNumber: Int = 1
    ): News

    /**
     * Save article in local storage
     * @param article - article which will be saved
     */

    suspend fun saveArticle(article: Article)

    /**
     * Get all saved articles
     */

    suspend fun getSavedArticles(): List<Article>
}
