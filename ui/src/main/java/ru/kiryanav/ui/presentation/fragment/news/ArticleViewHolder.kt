package ru.kiryanav.ui.presentation.fragment.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.ItemArticleBinding
import ru.kiryanav.ui.model.ArticleUI
import vlnny.base.adapter.BaseViewHolder

class ArticleViewHolder(
    private val binding: ItemArticleBinding,
    private val callback: OnArticleItemClick,
    private val isWithSaveIcon : Boolean
) :
    BaseViewHolder<ArticleUI>(binding.root) {

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

    override fun bindView(item: ArticleUI) {
        with (binding) {
            article = if(isWithSaveIcon) item else item.copy(isSaved = false)

            Glide.with(binding.root.context)
                .load(item.previewImageUrl)
                .error(Glide.with(binding.previewImage).load(R.drawable.preview_image))
                .into(binding.previewImage)

            root.apply {
                setOnClickListener {
                    callback.onItemClick(item)
                }

                setOnLongClickListener { root ->
                    callback.onLongClick(item, root)
                    true
                }
            }
            executePendingBindings()
        }

    }
}
