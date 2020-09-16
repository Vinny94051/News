package ru.kiryanav.ui.presentation.fragment.news

import android.view.ViewGroup
import ru.kiryanav.ui.model.ArticleUI
import vlnny.base.adapter.BaseAdapter

class ArticleAdapter(
    private val callback: OnArticleItemClick,
    private val withSaveIcon: Boolean
) :
    BaseAdapter<ArticleViewHolder, ArticleUI>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder.from(
            parent,
            callback,
            withSaveIcon
        )
}