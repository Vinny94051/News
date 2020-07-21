package ru.kiryanav.news.presentation.view.list

import ru.kiryanav.news.domain.model.ArticleUI

interface OnArticleItemClick {
    fun popupSave(item : ArticleUI)
}