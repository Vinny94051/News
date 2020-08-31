package ru.kiryanav.news.presentation.ui.list.adapter

import android.view.ViewGroup
import ru.kiryanav.news.domain.model.ArticleUI
import ru.kiryanav.news.presentation.ui.list.OnArticleItemClick
import ru.kiryanav.news.presentation.ui.list.viewholder.ArticleViewHolder
import vlnny.base.adapter.BaseAdapter

class ArticleAdapter(private val callback : OnArticleItemClick) : BaseAdapter<ArticleViewHolder, ArticleUI>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder.from(parent, callback)
}