package ru.kiryanav.ui.presentation.ui.list.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.ItemSourceBinding
import ru.kiryanav.ui.model.ArticleSourceUI
import ru.kiryanav.ui.presentation.ui.list.OnSourceItemClick
import vlnny.base.adapter.BaseViewHolder

class SourceViewHolder(private val binding: ItemSourceBinding, private val callback: OnSourceItemClick) : BaseViewHolder<ArticleSourceUI>(binding.root) {
    override fun bindView(item: ArticleSourceUI) {
        binding.apply {
            source = item

            root.setOnClickListener {
                callback.chooseItem(item)
            }

            executePendingBindings()
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            callback: OnSourceItemClick
        ) =
            SourceViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_source,
                    parent,
                    false
                ),
                callback
            )
    }

}