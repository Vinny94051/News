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
        sources: List<ArticleSource>,
        language: String,
        pageNumber: Int
    ) =
        newsRepo.loadNews(query, from, to, sources, language, pageNumber)

    override suspend fun saveArticle(article: Article) {
        articleRepository.saveArticle(article)
    }

    override suspend fun getSavedArticles(): List<Article> =
        articleRepository.getArticlesAll()

    override suspend fun getSourcesByLanguage(language: String): List<ArticleSource> =
        newsRepo.getSourcesByLanguage(language)

    override suspend fun getSavedSources(): List<ArticleSource> =
        articleRepository.getAllSources()


    override suspend fun saveSources(sources: List<ArticleSource>) =
        articleRepository.insertSources(sources)

    override suspend fun deleteSource(source: ArticleSource) {
        articleRepository.deleteSource(source)
    }

}