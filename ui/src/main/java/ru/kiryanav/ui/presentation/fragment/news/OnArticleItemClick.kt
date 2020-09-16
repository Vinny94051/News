package ru.kiryanav.ui.presentation.fragment.news

import android.view.View
import ru.kiryanav.ui.model.ArticleUI

interface OnArticleItemClick {
    fun onLongClick(article: ArticleUI, itemView: View)
    fun onItemClick(article : ArticleUI)
}