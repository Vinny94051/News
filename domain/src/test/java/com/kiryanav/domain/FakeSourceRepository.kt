package com.kiryanav.domain

import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.repoApi.SourceRepository
import vlnny.base.data.model.ResponseResult

class FakeSourceRepository : SourceRepository {
    override suspend fun getSavedSources(): ResponseResult<List<ArticleSource>, Error> {
        return ResponseResult.Success(createFakeSources())
    }

    private fun createFakeSources(): List<ArticleSource> {
        val sources = mutableListOf<ArticleSource>()

        for (i in 0..10) {
            sources.add(
                ArticleSource(
                    "id".plus(i),
                    "name".plus(i),
                    "description".plus(i),
                    "url".plus(i),
                    "category".plus(i),
                    "lang".plus(i),
                    "country".plus(i)
                )
            )
        }

        return sources
    }

    override suspend fun insertSources(source: List<ArticleSource>): ResponseResult<Unit, Error> {
       return ResponseResult.Success(Unit)
    }

    override suspend fun deleteSource(source: ArticleSource): ResponseResult<Unit, Error> {
        return ResponseResult.Success(Unit)
    }
}