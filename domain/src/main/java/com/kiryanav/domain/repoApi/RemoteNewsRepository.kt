package com.kiryanav.domain.repoApi

import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy
import com.kiryanav.domain.model.ArticleSource

interface RemoteNewsRepository {
    suspend fun getEverything(
        query: String,
        from: String,
        to: String,
        language: String,
        pageNumber: Int,
        sortBy: SortBy = SortBy.PUBLISHED_AT
    ): News

    suspend fun getSourcesByLanguage(
        language: String
    ): List<ArticleSource>

}