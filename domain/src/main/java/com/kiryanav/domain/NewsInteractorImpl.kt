package com.kiryanav.domain

import com.kiryanav.domain.error.NewsError
import com.kiryanav.domain.error.SourceError
import com.kiryanav.domain.model.*
import com.kiryanav.domain.repoApi.ArticleRepository
import com.kiryanav.domain.repoApi.NewsRepository
import com.kiryanav.domain.repoApi.SourceRepository
import vlnny.base.data.model.*
import vlnny.base.ext.updateFrom


class NewsInteractorImpl(
    private val newsRepository: NewsRepository,
    private val articleRepository: ArticleRepository,
    private val sourceRepository: SourceRepository
) : NewsInteractor {

    override suspend fun getNews(
        query: String?, from: String?, to: String?, sources: List<ArticleSource>,
        language: String?, pageNumber: Int
    ): ResponseResult<NewsWrapper, NewsError> {

        var totalResult: Int? = null

        val unsavedWrappedArticles =
            newsRepository.getNews(query, from, to, sources, language, pageNumber)
                .mapIfSuccess { news ->
                    totalResult = news.resultNumber
                    ResponseResult.Success(news.articles.map { article ->
                        SavedArticleWrapper(false, article)
                    })
                }

        val savedWrappedArticles =
            articleRepository.getAllSavedArticles()
                .mapIfSuccess { articles ->
                    ResponseResult.Success(articles.map { article ->
                        SavedArticleWrapper(true, article)
                    })
                }

        return ResponseResult.Success(
            NewsWrapper(totalResult
                ?: return ResponseResult.Error(unsavedWrappedArticles.error()),
                unsavedWrappedArticles.successValue()
                    ?.updateFrom(
                        savedWrappedArticles.successValue()
                            ?: return ResponseResult.Error(savedWrappedArticles.error())
                    ) { item1, item2 ->
                        item1.item.title == item2.item.title
                    }
                    ?: return ResponseResult.Error(unsavedWrappedArticles.error())
            )
        )
    }

    override suspend fun getSources(): ResponseResult<List<SavedArticleSourceWrapper>, SourceError> {

        val unsavedWrappedSources =
            newsRepository.getSources().mapIfSuccess { sources ->
                ResponseResult.Success(sources.map { source ->
                    SavedArticleSourceWrapper(false, source)
                })
            }

        val savedWrappedSources = sourceRepository.getSavedSources().mapIfSuccess { sources ->
            ResponseResult.Success(sources.map { source ->
                SavedArticleSourceWrapper(true, source)
            })
        }

        return ResponseResult.Success(
            unsavedWrappedSources.successValue()
                ?.updateFrom(
                    savedWrappedSources.successValue() ?: return savedWrappedSources
                ) { item1, item2 ->
                    item1.item.name == item2.item.name
                } ?: return unsavedWrappedSources)
    }

    override suspend fun saveArticle(article: Article) =
        articleRepository.saveArticle(article)

    override suspend fun deleteArticle(article: Article) =
        articleRepository.deleteArticle(article)

    override suspend fun getSavedArticles(): ResponseResult<List<SavedArticleWrapper>, NewsError> =
        articleRepository.getAllSavedArticles().mapIfSuccess { articles ->
            ResponseResult.Success(articles.map { article ->
                SavedArticleWrapper(true, article)
            })
        }

    override suspend fun getSourcesByLanguage(language: String): ResponseResult<List<ArticleSource>, SourceError> =
        newsRepository.getSources(language)

    override suspend fun getSavedSources(): ResponseResult<List<ArticleSource>, SourceError> =
        sourceRepository.getSavedSources()

    override suspend fun saveSources(sources: List<ArticleSource>) =
        sourceRepository.insertSources(sources)

    override suspend fun deleteSource(source: ArticleSource) =
        sourceRepository.deleteSource(source)
}


