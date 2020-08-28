package ru.kiryanav.news.domain

import io.reactivex.Completable
import io.reactivex.Single
import ru.kiryanav.news.Constants
import ru.kiryanav.news.domain.model.ArticleUI
import ru.kiryanav.news.domain.model.NewsUIModel

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
    ): NewsUIModel

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
