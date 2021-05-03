package ru.kiryanav.ui.presentation.fragment.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.ItemArticleHeaderBinding
import ru.kiryanav.ui.model.ArticleItem
import vlnny.base.adapter.BaseViewHolder

class ArticleHeaderViewHolder(
    private val binding: ItemArticleHeaderBinding
) : BaseViewHolder<ArticleItem.DateHeader>(binding.root) {

    companion object {
        fun from(parent: ViewGroup) = ArticleHeaderViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_article_header,
                parent,
                false
            )
        )
    }

    override fun bindView(item: ArticleItem.DateHeader) {
        binding.apply {
            header = item
            executePendingBindings()
        }
    }
}