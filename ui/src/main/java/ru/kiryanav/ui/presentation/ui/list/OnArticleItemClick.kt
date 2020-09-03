package ru.kiryanav.ui.presentation.ui.list

import android.view.View
import ru.kiryanav.ui.model.ArticleUI

interface OnArticleItemClick {
    fun onLongClick(article: ArticleUI, itemView: View)
    fun onItemClick(article : ArticleUI)
}