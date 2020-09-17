package ru.kiryanav.ui.model

import com.kiryanav.domain.model.Article

sealed class ArticleItem() {
    data class ArticleUI(
        val author: String,
        val title: String,
        val description: String,
        val articleUrl: String,
        val previewImageUrl: String,
        val publishedAt: String,
        val item: Article,
        val isSaved: Boolean
    ) : ArticleItem()

    data class DateHeader(
        val date: String
    ) : ArticleItem()
}



