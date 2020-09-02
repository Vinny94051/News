package com.kiryanav.domain.repoApi

import com.kiryanav.domain.model.Article


interface IArticleRepository {

    /**
     * Save article into database
     * @param article - this article will be saved
     */

    suspend fun saveArticle(article: Article)

    /**
     * Get all saved articles
     */

    suspend fun getAll(): List<Article>
}