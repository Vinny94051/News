package com.kiryanav.domain

import com.kiryanav.domain.mapper.toDomain
import com.kiryanav.domain.mapper.toUIModel
import com.kiryanav.domain.repoApi.IArticleRepository
import com.kiryanav.domain.repoApi.INewsRepository
import ru.kiryanav.ui.domainApi.INewsInteractor
import ru.kiryanav.ui.model.ArticleUI

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
            .toUIModel()

    override suspend fun saveArticle(article: ArticleUI) {
        articleRepository.saveArticle(article.toDomain())
    }


    override suspend fun getSavedArticles(): List<ArticleUI> =
        articleRepository.getAll().map { article ->
            article.toUIModel()
        }
}