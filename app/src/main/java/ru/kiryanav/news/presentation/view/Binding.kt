package ru.kiryanav.news.presentation.view

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kiryanav.news.domain.model.ArticleUI
import ru.kiryanav.news.presentation.view.list.OnArticleItemClick
import ru.kiryanav.news.presentation.view.list.adapter.ArticleAdapter
import vlnny.base.ext.hide
import vlnny.base.ext.show

@BindingAdapter("articles", "articleCallback")
fun bindArticles(recyclerView: RecyclerView, articles: List<ArticleUI>?, callback : OnArticleItemClick) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = ArticleAdapter(callback)
    }

    (recyclerView.adapter as ArticleAdapter).updateList(articles ?: emptyList())
}

@BindingAdapter("isVisible")
fun bindVisibility(view : View, isVisible : Boolean){
    if(isVisible)
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