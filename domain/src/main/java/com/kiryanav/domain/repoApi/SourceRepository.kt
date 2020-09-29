package com.kiryanav.domain.repoApi

import com.kiryanav.domain.Error
import com.kiryanav.domain.model.ArticleSource
import vlnny.base.data.model.ResponseResult

interface SourceRepository {
    suspend fun getSavedSources(): ResponseResult<List<ArticleSource>, Error>

    suspend fun insertSources(source: List<ArticleSource>) : ResponseResult<Unit, Error>

    suspend fun deleteSource(source: ArticleSource) : ResponseResult<Unit, Error>
}