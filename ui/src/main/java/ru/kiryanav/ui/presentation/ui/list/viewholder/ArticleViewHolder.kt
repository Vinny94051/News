package ru.kiryanav.ui.presentation.ui.list.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import ru.kiryanav.ui.Constants
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.ItemArticleBinding
import ru.kiryanav.ui.model.ArticleUI
import ru.kiryanav.ui.presentation.ui.list.OnArticleItemClick
import vlnny.base.adapter.BaseViewHolder
import vlnny.base.ext.openLink

class ArticleViewHolder(
    private val binding: ItemArticleBinding,
    private val callback: OnArticleItemClick
) :
    BaseViewHolder<ArticleUI>(binding.root) {

    companion object {
        fun from(parent: ViewGroup, callback: OnArticleItemClick) = ArticleViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_article,
                parent,
                false
            ),
            callback
        )
    }

    override fun bindView(item: ArticleUI) {
        binding.apply {
            article = item
            if (item.previewImageUrl != Constants.EMPTY_STRING) {
                Glide.with(binding.root.context)
                    .load(item.previewImageUrl)
                    .error(Glide.with(binding.previewImage).load(R.drawable.preview_image))
                    .into(binding.previewImage)
            }
            root.apply {
                setOnClickListener { root ->
                    root.context.openLink(item.articleUrl)
                }

                setOnLongClickListener { root ->
                    showPopup(root, item)
                    true
                }
            }
            executePendingBindings()
        }
    }

    private fun showPopup(view: View, listItem: ArticleUI) {
        PopupMenu(view.context, view).apply {
            inflate(R.menu.item_article_popup)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.save -> {
                        callback.popupSave(listItem)
                    }
                }
                true
            }
        }.show()
    }
}