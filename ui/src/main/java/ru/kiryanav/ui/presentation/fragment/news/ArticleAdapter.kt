package ru.kiryanav.ui.presentation.fragment.news

import android.view.ViewGroup
import ru.kiryanav.ui.model.ArticleItem
import vlnny.base.adapter.BaseAdapter
import vlnny.base.adapter.BaseViewHolder

class ArticleAdapter(
    private val callback: OnArticleItemClick,
    private val withSaveIcon: Boolean
) :
    BaseAdapter<BaseViewHolder<ArticleItem>, ArticleItem>() {

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is ArticleItem.ArticleUI -> 0
            is ArticleItem.DateHeader -> 1
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ArticleItem>  =
        when (viewType) {
            0 -> ArticleViewHolder.from(
                parent,
                callback,
                withSaveIcon
            ) as BaseViewHolder<ArticleItem>

            1 -> ArticleHeaderViewHolder.from(
                parent
            ) as BaseViewHolder<ArticleItem>
            else -> throw ClassCastException("Unknown viewType $viewType")
        }

}