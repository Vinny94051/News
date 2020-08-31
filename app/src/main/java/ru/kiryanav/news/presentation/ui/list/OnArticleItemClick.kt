package ru.kiryanav.news.presentation.ui.list

import ru.kiryanav.news.domain.model.ArticleUI

interface OnArticleItemClick {
    fun popupSave(item : ArticleUI)
}