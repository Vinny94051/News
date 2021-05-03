package com.kiryanav.domain.repoApi

import com.kiryanav.domain.error.NewsError
import com.kiryanav.domain.error.SourceError
import com.kiryanav.domain.model.ArticleSource
import vlnny.base.data.model.ResponseResult

interface SourceRepository {
    suspend fun getSavedSources(): ResponseResult<List<ArticleSource>, SourceError>

    suspend fun insertSources(source: List<ArticleSource>) : ResponseResult<Unit, SourceError>

    suspend fun deleteSource(source: ArticleSource) : ResponseResult<Unit, SourceError>
}