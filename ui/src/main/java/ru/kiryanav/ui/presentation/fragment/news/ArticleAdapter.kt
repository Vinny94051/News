package ru.kiryanav.ui.presentation.fragment.news

import android.view.ViewGroup
import ru.kiryanav.ui.model.ArticleItem
import ru.kiryanav.ui.presentation.fragment.news.callback.OnArticleItemClick
import vlnny.base.adapter.BaseAdapter
import vlnny.base.adapter.BaseViewHolder


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

    override fun setViewHolders(parent: ViewGroup) =
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

    companion object {
        private const val ARTICLE_UI_VIEW_TYPE = 0
        private const val DATE_HEADER_VIEW_TYPE = 1
    }
}



