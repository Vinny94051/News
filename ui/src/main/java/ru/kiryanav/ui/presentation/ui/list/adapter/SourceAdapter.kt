package ru.kiryanav.ui.presentation.ui.list.adapter

import android.view.ViewGroup
import ru.kiryanav.ui.model.ArticleSourceUI
import ru.kiryanav.ui.presentation.ui.list.OnSourceItemClick
import ru.kiryanav.ui.presentation.ui.list.viewholder.SourceViewHolder
import vlnny.base.adapter.BaseAdapter

class SourceAdapter(private val callback : OnSourceItemClick) : BaseAdapter<SourceViewHolder, ArticleSourceUI>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder =
        SourceViewHolder.from(parent, callback)
}