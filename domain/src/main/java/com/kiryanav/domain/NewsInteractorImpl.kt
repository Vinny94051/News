package com.kiryanav.domain

import com.kiryanav.domain.model.*
import com.kiryanav.domain.repoApi.LocalNewsRepository
import com.kiryanav.domain.repoApi.RemoteNewsRepository
import vlnny.base.ext.updateFrom


class NewsInteractorImpl(
    private val newsRepository: RemoteNewsRepository,
    private val articleRepository: LocalNewsRepository
) : NewsInteractor {

    override suspend fun getNews(
        query: String?,
        from: String?,
        to: String?,
        sources: List<ArticleSource>,
        language: String?,
        pageNumber: Int
    ): NewsWrapper {
        val news = newsRepository.getNews(query, from, to, sources, language, pageNumber)

        val unsavedWrappedArticles = news.articles.map { article ->
            SavedArticleWrapper(false, article)
        }

        val savedWrappedArticles = articleRepository.getAllSavedArticles().map { article ->
            SavedArticleWrapper(true, article)
        }

        return NewsWrapper(news.resultNumber ?: 0,
            unsavedWrappedArticles.updateFrom(savedWrappedArticles) { item1, item2 ->
                item1.item.title == item2.item.title
            })
    }

    override suspend fun getSources(): List<SavedArticleSourceWrapper> {

        val unsavedWrappedSources = newsRepository.getSourcesByLanguage("ru").map { source ->
            SavedArticleSourceWrapper(false, source)
        }

        val savedWrappedSource = articleRepository.getSavedSources().map { source ->
            SavedArticleSourceWrapper(true, source)
        }

        return unsavedWrappedSources.updateFrom(
            savedWrappedSource
        ) { item1, item2 ->
            item1.item.name == item2.item.name
        }
    }

    override suspend fun deleteArticle(article: Article) {
        articleRepository.deleteArticle(article)
    }

    override suspend fun saveArticle(article: Article) {
        articleRepository.saveArticle(article)
    }

    override suspend fun getSavedArticles(): List<SavedArticleWrapper> =
        articleRepository.getAllSavedArticles().map { article ->
            SavedArticleWrapper(true, article)
        }

    override suspend fun getSourcesByLanguage(language: String): List<ArticleSource> =
        newsRepository.getSourcesByLanguage(language)

    override suspend fun getSavedSources(): List<ArticleSource> =
        articleRepository.getSavedSources()


    override suspend fun saveSources(sources: List<ArticleSource>) =
        articleRepository.insertSources(sources)

    override suspend fun deleteSource(source: ArticleSource) {
        articleRepository.deleteSource(source)
    }

}