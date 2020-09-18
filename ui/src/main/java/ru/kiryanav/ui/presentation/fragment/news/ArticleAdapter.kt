package ru.kiryanav.ui.presentation.fragment.news

import android.view.ViewGroup
import ru.kiryanav.ui.model.ArticleItem
import vlnny.base.adapter.BaseAdapter
import vlnny.base.adapter.BaseViewHolder
import vlnny.base.adapter.MultiItemRecyclerViewHolderCreator

class ArticleAdapter(
    private val callback: OnArticleItemClick,
    private val withSaveIcon: Boolean
) :
    BaseAdapter<BaseViewHolder<ArticleItem>, ArticleItem>() {

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is ArticleItem.ArticleUI -> ARTICLE_UI_VIEW_TYPE
            is ArticleItem.DateHeader -> DATE_HEADER_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ArticleItem> {
        return MultiItemRecyclerViewHolderCreator<ArticleItem>(
            hashMapOf(
                Pair(
                    ARTICLE_UI_VIEW_TYPE,
                    ArticleViewHolder.from(parent, callback, withSaveIcon)
                ),
                Pair(
                    DATE_HEADER_VIEW_TYPE,
                    ArticleHeaderViewHolder.from(parent)
                )
            )
        ).onCreateViewHolder(viewType)
    }

    companion object {
        private const val ARTICLE_UI_VIEW_TYPE = 0
        private const val DATE_HEADER_VIEW_TYPE = 1
    }
}

