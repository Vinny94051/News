package ru.kiryanav.news.domain.model

data class ArticleUI(
    val sourceId : String,
    val sourceName : String,
    val author : String,
    val title : String,
    val description : String,
    val articleUrl : String,
    val previewImageUrl : String,
    val publishedAt : String,
    val content : String
)
