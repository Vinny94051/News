package com.kiryanav.domain.repoApi

import com.kiryanav.domain.error.NewsError
import com.kiryanav.domain.model.Article
import vlnny.base.data.model.ResponseResult

interface ArticleRepository {
    /**
     * Save article into database
     * @param article - this article will be saved
     */

    suspend fun saveArticle(article: Article) : ResponseResult<Unit, NewsError>

    /**
     * Get all saved articles
     */

    suspend fun getAllSavedArticles(): ResponseResult<List<Article>, NewsError>

    suspend fun deleteArticle(article: Article) : ResponseResult<Unit, NewsError>
}