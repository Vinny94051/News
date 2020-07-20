package ru.kiryanav.news.presentation.view

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kiryanav.news.domain.model.ArticleUI
import ru.kiryanav.news.presentation.view.list.adapter.ArticleAdapter
import vlnny.base.ext.hide
import vlnny.base.ext.show

@BindingAdapter("articles")
fun bindArticles(recyclerView: RecyclerView, articles: List<ArticleUI>?) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = ArticleAdapter()
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