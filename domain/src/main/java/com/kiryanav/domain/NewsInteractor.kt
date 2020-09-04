package com.kiryanav.domain

import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.repoApi.LocalNewsRepository
import com.kiryanav.domain.repoApi.RemoteNewsRepository


class NewsInteractor(
    private val newsRepo: RemoteNewsRepository,
    private val articleRepository: LocalNewsRepository
) : INewsInteractor {

    override suspend fun getNews(
        query: String,
        from: String,
        to: String,
        language: String,
        pageNumber: Int
    ) =
        newsRepo.getEverything(query, from, to, language, pageNumber)

    override suspend fun saveArticle(article: Article) {
        articleRepository.saveArticle(article)
    }

    override suspend fun getSavedArticles(): List<Article> =
        articleRepository.getAll()

    override suspend fun getSourcesByLanguage(language: String): List<ArticleSource> =
        newsRepo.getSourcesByLanguage(language)

}