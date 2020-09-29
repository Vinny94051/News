package com.kiryanav.domain

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

    //TODO like getSources
    override suspend fun getNews(
        query: String?,
        from: String?,
        to: String?,
        sources: List<ArticleSource>,
        language: String?,
        pageNumber: Int
    ): ResponseResult<NewsWrapper, Error> {
        val response: ResponseResult<News, Error> =
            newsRepository.getNews(query, from, to, sources, language, pageNumber)

        val unsavedWrappedArticles: List<SavedArticleWrapper>

        when (response) {
            is ResponseResult.Success<News> -> {
                unsavedWrappedArticles = response.value.articles.map { article ->
                    SavedArticleWrapper(false, article)
                }
            }
            is ResponseResult.Error -> return response
        }

        val savedArticles: ResponseResult<List<Article>, Error> =
            articleRepository.getAllSavedArticles()

        val savedWrappedArticles: List<SavedArticleWrapper>

        when (savedArticles) {
            is ResponseResult.Success<List<Article>> -> savedWrappedArticles =
                savedArticles.value.map { article ->
                    SavedArticleWrapper(true, article)
                }
            is ResponseResult.Error -> return savedArticles
        }

        return ResponseResult.Success(
            NewsWrapper(
                response.value.resultNumber ?: 0,
                unsavedWrappedArticles.updateFrom(savedWrappedArticles) { item1, item2 ->
                    item1.item.title == item2.item.title
                })
        )
    }

    override suspend fun getSources(): ResponseResult<List<SavedArticleSourceWrapper>, Error> {

        val unsavedWrappedSources =
            newsRepository.getSourcesByLanguage("ru").mapIfSuccess { sources ->
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


    override suspend fun getSavedArticles(): ResponseResult<List<SavedArticleWrapper>, Error> =
        articleRepository.getAllSavedArticles().mapIfSuccess { articles ->
            ResponseResult.Success(articles.map { article ->
                SavedArticleWrapper(true, article)
            })
        } ?: ResponseResult.Error()

    override suspend fun getSourcesByLanguage(language: String): ResponseResult<List<ArticleSource>, Error> =
        newsRepository.getSourcesByLanguage(language)

    override suspend fun getSavedSources(): ResponseResult<List<ArticleSource>, Error> =
        sourceRepository.getSavedSources()


    override suspend fun saveSources(sources: List<ArticleSource>) =
        sourceRepository.insertSources(sources)

    override suspend fun deleteSource(source: ArticleSource) =
        sourceRepository.deleteSource(source)
}