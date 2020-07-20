package ru.kiryanav.news.domain.model

data class NewsUIModel(
    val resultNumber : String,
    val articles : List<ArticleUI>
)