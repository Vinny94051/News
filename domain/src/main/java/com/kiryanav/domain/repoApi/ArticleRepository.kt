package com.kiryanav.domain.repoApi

import com.kiryanav.domain.Error
import com.kiryanav.domain.model.Article
import vlnny.base.data.model.ResponseResult

interface ArticleRepository {
    /**
     * Save article into database
     * @param article - this article will be saved
     */

    suspend fun saveArticle(article: Article) : ResponseResult<Unit, Error>

    /**
     * Get all saved articles
     */

    suspend fun getAllSavedArticles(): ResponseResult<List<Article>,Error>

    suspend fun deleteArticle(article: Article) : ResponseResult<Unit, Error>
}