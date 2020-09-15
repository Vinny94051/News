package ru.kiryanav.ui.presentation.ui

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kiryanav.ui.model.ArticleSourceUI
import ru.kiryanav.ui.model.ArticleUI
import ru.kiryanav.ui.presentation.ui.list.OnArticleItemClick
import ru.kiryanav.ui.presentation.ui.list.OnSourceItemClick
import ru.kiryanav.ui.presentation.ui.list.adapter.ArticleAdapter
import ru.kiryanav.ui.presentation.ui.list.adapter.SourceAdapter
import vlnny.base.ext.hide
import vlnny.base.ext.show

@BindingAdapter("articles", "articleCallback", "isWithSaveIcon")
fun bindArticles(
    recyclerView: RecyclerView,
    articles: List<ArticleUI>?,
    callback: OnArticleItemClick,
    isWithSaveIcon : Boolean = true
) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = ArticleAdapter(callback, isWithSaveIcon)
    }

    (recyclerView.adapter as ArticleAdapter).updateList(articles ?: emptyList())
}

@BindingAdapter("isVisible")
fun bindVisibility(view: View, isVisible: Boolean) {
    if (isVisible)
        view.show()
    else
        view.hide()
}

@BindingAdapter("app:onClick")
fun setOnClick(
    view: View,
    clickListener: View.OnClickListener?
) {
    view.setOnClickListener(clickListener)
    view.isClickable = true
}

@BindingAdapter("sources", "sourcesCallback")
fun bindSources(
    recyclerView: RecyclerView,
    sources: List<ArticleSourceUI>?,
    sourcesCallback: OnSourceItemClick
) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = SourceAdapter(sourcesCallback)
    } else {
        (recyclerView.adapter as SourceAdapter).updateList(sources ?: emptyList())
    }
}