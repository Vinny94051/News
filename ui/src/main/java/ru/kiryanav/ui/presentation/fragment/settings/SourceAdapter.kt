package ru.kiryanav.ui.presentation.fragment.settings

import android.view.ViewGroup
import ru.kiryanav.ui.model.ArticleSourceUI
import vlnny.base.adapter.BaseAdapter
import vlnny.base.adapter.BaseViewHolder

class SourceAdapter(private val callback: OnSourceItemClick) :
    BaseAdapter<SourceViewHolder, ArticleSourceUI>() {

    override fun setViewHolders(parent: ViewGroup):
            HashMap<Int, BaseViewHolder<out ArticleSourceUI>> =

        hashMapOf(
            Pair(
                ONE_ITEM_VIEW_TYPE,
                SourceViewHolder.from(
                    parent,
                    callback
                )
            )
        )
}

