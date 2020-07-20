package ru.kiryanav.news.presentation.view.list.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import ru.kiryanav.news.Constants
import ru.kiryanav.news.R
import ru.kiryanav.news.databinding.ItemArticleBinding
import ru.kiryanav.news.domain.model.ArticleUI
import vlnny.base.adapter.BaseViewHolder
import vlnny.base.ext.openLink

class ArticleViewHolder(private val binding: ItemArticleBinding) :
    BaseViewHolder<ArticleUI>(binding.root) {

    companion object {
        fun from(parent: ViewGroup) = ArticleViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_article,
                parent,
                false
            )
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
            binding.root.setOnClickListener { root ->
                root.context.openLink(item.articleUrl)
            }
            executePendingBindings()
        }
    }
}