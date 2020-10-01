package com.kiryanav.domain

import com.kiryanav.domain.model.Article
import com.kiryanav.domain.repoApi.ArticleRepository
import vlnny.base.data.model.ResponseResult

class FakeArticleRepository : ArticleRepository {
    override suspend fun saveArticle(article: Article): ResponseResult<Unit, Error> {
        return ResponseResult.Success(Unit)
    }

    override suspend fun getAllSavedArticles(): ResponseResult<List<Article>, Error> {
        return ResponseResult.Success(createFakeArticles())
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

    override suspend fun deleteArticle(article: Article): ResponseResult<Unit, Error> {
        return ResponseResult.Success(Unit)
    }
}