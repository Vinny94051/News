package ru.kiryanav.news.data.repository

import ru.kiryanav.news.domain.model.ArticleUI

interface IArticleRepository {

    /**
     * Save article into database
     * @param article - this article will be saved
     */

    suspend fun saveArticle(article: ArticleUI)

    /**
     * Get all saved articles
     */

    suspend fun getAll(): List<ArticleUI>
}