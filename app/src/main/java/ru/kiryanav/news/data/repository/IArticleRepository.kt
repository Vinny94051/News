package ru.kiryanav.news.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.kiryanav.news.domain.model.ArticleUI

interface IArticleRepository {

    /**
     * Save article into database
     * @param article - this article will be saved
     */

    fun saveArticle(article: ArticleUI): Completable

    /**
     * Get all saved articles
     */

    fun getAll(): Single<List<ArticleUI>>
}