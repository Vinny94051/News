package ru.kiryanav.ui.presentation.ui

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kiryanav.ui.model.ArticleUI
import ru.kiryanav.ui.presentation.ui.list.OnArticleItemClick
import ru.kiryanav.ui.presentation.ui.list.adapter.ArticleAdapter
import vlnny.base.ext.hide
import vlnny.base.ext.show

@BindingAdapter("articles", "articleCallback")
fun bindArticles(recyclerView: RecyclerView, articles: List<ArticleUI>?, callback : OnArticleItemClick) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = ArticleAdapter(callback)
    }

    (recyclerView.adapter as ArticleAdapter).updateList(articles ?: emptyList<ArticleUI>())
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