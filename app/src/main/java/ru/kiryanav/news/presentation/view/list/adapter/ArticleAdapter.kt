package ru.kiryanav.news.presentation.view.list.adapter

import android.view.ViewGroup
import ru.kiryanav.news.domain.model.ArticleUI
import ru.kiryanav.news.presentation.view.list.viewholder.ArticleViewHolder
import vlnny.base.adapter.BaseAdapter

class ArticleAdapter : BaseAdapter<ArticleViewHolder, ArticleUI>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder.from(parent)
}