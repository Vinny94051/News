package ru.kiryanav.ui.presentation.ui.list

import ru.kiryanav.ui.model.ArticleSourceUI

interface OnSourceItemClick {
    fun chooseItem(item : ArticleSourceUI)
}