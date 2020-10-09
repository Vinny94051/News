package com.kiryanav.domain

import com.kiryanav.domain.error.NewsError
import com.kiryanav.domain.model.Article
import com.kiryanav.domain.repoApi.ArticleRepository
import vlnny.base.data.model.ResponseResult
import java.util.*

class FakeArticleRepository(internal val articlesNumber : Int) : ArticleRepository {
    override suspend fun saveArticle(article: Article): ResponseResult<Unit, NewsError> {
        return ResponseResult.Success(Unit)
    }

    override suspend fun getAllSavedArticles(): ResponseResult<List<Article>, NewsError> {
        return ResponseResult.Success(createFakeArticles())
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

    override suspend fun deleteArticle(article: Article): ResponseResult<Unit, NewsError> {
        return ResponseResult.Success(Unit)
    }
}