package com.kiryanav.domain.model

data class NewsWrapper(
    val totalResult: Int,
    val articles: List<SavedArticleWrapper>
)