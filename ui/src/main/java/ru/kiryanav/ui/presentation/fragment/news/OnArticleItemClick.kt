package ru.kiryanav.ui.presentation.fragment.news


import ru.kiryanav.ui.model.ArticleItem

interface OnArticleItemClick {
    fun onSaveItemClick(article: ArticleItem.ArticleUI, isSaved: Boolean)
    fun onItemClick(article: ArticleItem.ArticleUI)
    fun onShareItemClick(article: ArticleItem.ArticleUI)
}