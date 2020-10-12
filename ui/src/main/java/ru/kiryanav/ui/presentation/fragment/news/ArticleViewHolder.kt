package ru.kiryanav.ui.presentation.fragment.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import org.koin.core.KoinComponent
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.ItemArticleBinding
import ru.kiryanav.ui.model.ArticleItem
import vlnny.base.adapter.BaseViewHolder
import vlnny.base.ext.hide

class ArticleViewHolder(
    private val binding: ItemArticleBinding,
    private val callback: OnArticleItemClick,
    private val isWithSaveIcon: Boolean
) :
    BaseViewHolder<ArticleItem.ArticleUI>(binding.root), KoinComponent {

    companion object {
        fun from(
            parent: ViewGroup,
            callback: OnArticleItemClick,
            withSaveIcon: Boolean
        ) = ArticleViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_article,
                parent,
                false
            ),
            callback,
            withSaveIcon
        )
    }

    override fun bindView(item: ArticleItem.ArticleUI) {
        with(binding) {
            article = item

            if (!isWithSaveIcon) {
                isSavedLabel.hide()
            } else {
                isSavedLabel.setOnClickListener {
                    callback.onSaveItemClick(item, article?.isSaved != true)
                }
            }

            Glide.with(binding.root.context)
                .load(item.previewImageUrl)
                .error(Glide.with(binding.previewImage).load(R.drawable.preview_image))
                .into(binding.previewImage)

            root.setOnClickListener {
                callback.onItemClick(item)
            }

            share.setOnClickListener {
                callback.onShareItemClick(item)
            }

            executePendingBindings()
        }

    }
}
