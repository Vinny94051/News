package com.kiryanav.domain.repoApi

import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy

interface INewsRepository {
    suspend fun getEverything(
        query: String,
        from: String,
        to: String,
        language: String,
        dayNumber: Int,
        pageNumber: Int = 1,
        sortBy: SortBy = SortBy.PUBLISHED_AT
    ): News
}