package com.kiryanav.domain.repoApi

import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource


interface LocalNewsRepository {

    /**
     * Save article into database
     * @param article - this article will be saved
     */

    suspend fun saveArticle(article: Article)

    /**
     * Get all saved articles
     */

    suspend fun getAllSavedArticles(isLocalSavedFlagNeedToBeTrue : Boolean = true): List<Article>

    suspend fun getAllSources(): List<ArticleSource>

    suspend fun insertSources(source: List<ArticleSource>)

    suspend fun deleteSource(source: ArticleSource)

    suspend fun deleteArticle(article: Article)

}