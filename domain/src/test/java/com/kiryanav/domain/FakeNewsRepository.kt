package com.kiryanav.domain

import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy
import com.kiryanav.domain.repoApi.ArticleRepository
import com.kiryanav.domain.repoApi.NewsRepository
import com.kiryanav.domain.repoApi.SourceRepository
import vlnny.base.data.model.ResponseResult

class FakeNewsRepository : NewsRepository {
    override suspend fun getNews(
        query: String?,
        from: String?,
        to: String?,
        sources: List<ArticleSource>,
        language: String?,
        pageNumber: Int,
        sortBy: SortBy
    ): ResponseResult<News, Error> {
        return ResponseResult.Success(
            News(
                12,
                createFakeArticles()
            )
        )
    }

    private fun createFakeArticles(): List<Article> {
        val articles = mutableListOf<Article>()
        for (i in 0..10) {
            articles.add(
                Article(
                    "sourceId".plus(i),
                    "sourceName".plus(i),
                    "author".plus(i),
                    "title".plus(i),
                    "description".plus(i),
                    "articleUrl".plus(i),
                    "imageUrl".plus(i),
                    "date".plus(i),
                    "content".plus(i)
                )
            )
        }
        return articles
    }

    override suspend fun getSources(language: String): ResponseResult<List<ArticleSource>, Error> {
        return ResponseResult.Success(
            createFakeSources()
        )
    }

    private fun createFakeSources(): List<ArticleSource> {
        val sources = mutableListOf<ArticleSource>()
        for (i in 0..10) {
            ArticleSource(
                "id".plus(i),
                "name".plus(i),
                "description".plus(i),
                "url".plus(i),
                "category".plus(i),
                "lang".plus(i),
                "country".plus(i)
            )
        }
        return sources
    }


}