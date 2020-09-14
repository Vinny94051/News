package ru.kiryanav.ui.model

import com.kiryanav.domain.model.ArticleSource

data class ArticleSourceUI(
    val name: String?,
    val description: String?,
    val url: String?,
    val category: String?,
    val item: ArticleSource,
    var isSelected: Boolean

)
