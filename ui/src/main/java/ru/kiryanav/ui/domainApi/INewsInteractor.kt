package ru.kiryanav.ui.domainApi

import ru.kiryanav.ui.Constants
import ru.kiryanav.ui.model.ArticleUI
import ru.kiryanav.ui.model.NewsUI

interface INewsInteractor {

    /**
     * Get all news by
     * @param query like keyword in period
     * @param from date
     * @param to date
     * @param language - searching language
     */

    suspend fun getEverything(
        query: String,
        from: String,
        to: String,
        language: String,
        dayNumber: Int = Constants.ZERO_INT,
        pageNumber: Int = 1
    ): NewsUI

    /**
     * Save article in local storage
     * @param article - article which will be saved
     */

    suspend fun saveArticle(article: ArticleUI)

    /**
     * Get all saved articles
     */

    suspend fun getSavedArticles(): List<ArticleUI>
}
