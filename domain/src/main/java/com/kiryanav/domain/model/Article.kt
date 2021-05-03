package com.kiryanav.domain.model

import java.util.*

data class Article(
    val sourceId : String?,
    val sourceName : String?,
    val author : String?,
    val title : String?,
    val description : String?,
    val articleUrl : String?,
    val previewImageUrl : String?,
    val publishedAt : Date?,
    val content : String?
)
