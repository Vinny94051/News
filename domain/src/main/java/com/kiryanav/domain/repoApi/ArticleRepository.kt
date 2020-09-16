package com.kiryanav.domain.repoApi

import com.kiryanav.domain.model.Article

interface ArticleRepository {
    /**
     * Save article into database
     * @param article - this article will be saved
     */

    suspend fun saveArticle(article: Article)

    /**
     * Get all saved articles
     */

    suspend fun getAllSavedArticles(): List<Article>

    suspend fun deleteArticle(article: Article)
}