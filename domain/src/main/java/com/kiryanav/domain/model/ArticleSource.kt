package com.kiryanav.domain.model

data class ArticleSource(
    val id : String?,
    val name: String?,
    val description: String?,
    val url: String?,
    val category: String?,
    val lang : String?,
    val country : String?,
    var isLocalSaved : Boolean = false
)


