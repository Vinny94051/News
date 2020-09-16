package ru.kiryanav.ui.presentation.fragment.settings

import android.view.ViewGroup
import ru.kiryanav.ui.model.ArticleSourceUI
import vlnny.base.adapter.BaseAdapter

class SourceAdapter(private val callback: OnSourceItemClick) :
    BaseAdapter<SourceViewHolder, ArticleSourceUI>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder =
        SourceViewHolder.from(
            parent,
            callback
        )


}

