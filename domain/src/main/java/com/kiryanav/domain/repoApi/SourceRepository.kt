package com.kiryanav.domain.repoApi

import com.kiryanav.domain.model.ArticleSource

interface SourceRepository {
    suspend fun getSavedSources(): List<ArticleSource>

    suspend fun insertSources(source: List<ArticleSource>)

    suspend fun deleteSource(source: ArticleSource)
}