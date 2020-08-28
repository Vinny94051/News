package ru.kiryanav.news.domain

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.kiryanav.news.data.repository.IArticleRepository
import ru.kiryanav.news.data.repository.INewsRepository
import ru.kiryanav.news.domain.model.ArticleUI

class NewsInteractor(
    private val newsRepo: INewsRepository,
    private val articleRepository: IArticleRepository
) : INewsInteractor {

    override suspend fun getEverything(
        query: String,
        from: String,
        to: String,
        language: String,
        dayNumber: Int,
        pageNumber: Int
    ) =
        newsRepo.getEverything(query, from, to, language, dayNumber, pageNumber)


    override suspend fun saveArticle(article: ArticleUI) =
        articleRepository.saveArticle(article)


    override suspend fun getSavedArticles(): List<ArticleUI> =
        articleRepository.getAll()
}