package ru.kiryanav.ui.presentation.fragment.settings

import ru.kiryanav.ui.model.ArticleSourceUI

interface OnSourceItemClick {
    fun selectClick(item : ArticleSourceUI, isChecked : Boolean)
}