package ru.kiryanav.ui.presentation.ui.list

import ru.kiryanav.ui.model.ArticleUI

interface OnArticleItemClick {
    fun popupSave(item : ArticleUI)
}