package ru.kiryanav.news.domain

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.kiryanav.news.data.repository.IArticleRepository
import ru.kiryanav.news.data.repository.INewsRepository
import ru.kiryanav.news.domain.model.ArticleUI
import javax.inject.Inject

class NewsInteractor @Inject constructor(
    private val newsRepo: INewsRepository,
    private val articleRepository: IArticleRepository
) : INewsInteractor {

    override fun getEverything(
        query: String,
        from: String,
        to: String,
        language: String,
        dayNumber : Int,
        pageNumber: Int
    ) =
        newsRepo.getEverything(query, from, to, language, dayNumber, pageNumber)
            .subscribeOn(Schedulers.io())

    override fun saveArticle(article: ArticleUI) =
        articleRepository.saveArticle(article)
            .subscribeOn(Schedulers.io())

    override fun getSavedArticles(): Single<List<ArticleUI>> =
        articleRepository.getAll()
            .subscribeOn(Schedulers.io())
}