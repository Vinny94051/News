package com.kiryanav.domain

import com.kiryanav.domain.model.Article
import com.kiryanav.domain.repoApi.LocalRepository
import com.kiryanav.domain.repoApi.RemoteRepository


class NewsInteractor(
    private val newsRepo: RemoteRepository,
    private val articleRepository: LocalRepository
) : INewsInteractor {

    override suspend fun getNews(
        query: String,
        from: String,
        to: String,
        language: String,
        dayNumber: Int,
        pageNumber: Int
    ) =
        newsRepo.getEverything(query, from, to, language, dayNumber, pageNumber)

    override suspend fun saveArticle(article: Article) {
        articleRepository.saveArticle(article)
    }


    override suspend fun getSavedArticles(): List<Article> =
        articleRepository.getAll()
}