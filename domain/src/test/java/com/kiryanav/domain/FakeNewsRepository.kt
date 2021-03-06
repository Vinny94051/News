package com.kiryanav.domain

import com.kiryanav.domain.error.NewsError
import com.kiryanav.domain.error.SourceError
import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy
import com.kiryanav.domain.repoApi.NewsRepository
import vlnny.base.data.model.ResponseResult
import java.util.*

class FakeNewsRepository(internal val sourceNumber: Int, internal val articlesNumber: Int) :
    NewsRepository {
    override suspend fun getNews(
        query: String?,
        from: Date?,
        to: Date?,
        sources: List<ArticleSource>,
        language: String?,
        pageNumber: Int,
        sortBy: SortBy
    ): ResponseResult<News, NewsError> {
        return ResponseResult.Success(
            News(
                12,
                createFakeArticles()
            )
        )
    }

    private fun createFakeArticles(): List<Article> {
        val articles = mutableListOf<Article>()
        for (i in 0..articlesNumber) {
            articles.add(
                Article(
                    "sourceId".plus(i),
                    "sourceName".plus(i),
                    "author".plus(i),
                    "title".plus(i),
                    "description".plus(i),
                    "articleUrl".plus(i),
                    "imageUrl".plus(i),
                    Date(),
                    "content".plus(i)
                )
            )
        }
        return articles
    }

    override suspend fun getSources(language: String): ResponseResult<List<ArticleSource>, SourceError> {
        return ResponseResult.Success(createFakeSources())
    }

    private fun createFakeSources(): List<ArticleSource> {
        val sources = mutableListOf<ArticleSource>()
        for (i in 0..sourceNumber) {
         sources.add(  ArticleSource(
                "id".plus(i),
                "name".plus(i),
                "description".plus(i),
                "url".plus(i),
                "category".plus(i),
                "lang".plus(i),
                "country".plus(i)
            ))
        }
        return sources
    }


}