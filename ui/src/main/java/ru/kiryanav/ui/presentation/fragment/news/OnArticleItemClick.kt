package ru.kiryanav.ui.presentation.fragment.news

import ru.kiryanav.ui.model.ArticleItem

interface OnArticleItemClick {
    fun onCheckBoxClick(article: ArticleItem.ArticleUI, isSave : Boolean)
    fun onItemClick(article: ArticleItem.ArticleUI)
    fun onShareItemClick(article: ArticleItem.ArticleUI)
}