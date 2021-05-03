package ru.kiryanav.ui.presentation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kiryanav.ui.model.ArticleItem
import ru.kiryanav.ui.model.ArticleSourceUI
import ru.kiryanav.ui.presentation.fragment.news.ArticleAdapter
import ru.kiryanav.ui.presentation.fragment.news.NewsUIError
import ru.kiryanav.ui.presentation.fragment.news.callback.NewsErrorCallback
import ru.kiryanav.ui.presentation.fragment.news.callback.OnArticleItemClick
import ru.kiryanav.ui.presentation.fragment.settings.OnSourceItemClick
import ru.kiryanav.ui.presentation.fragment.settings.SettingsError
import ru.kiryanav.ui.presentation.fragment.settings.SourceAdapter
import vlnny.base.ext.hide
import vlnny.base.ext.show

@BindingAdapter("articles", "articleCallback", "isWithSaveIcon")
fun bindArticles(
    recyclerView: RecyclerView,
    articles: List<ArticleItem>?,
    callback: OnArticleItemClick,
    isWithSaveIcon: Boolean = true
) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter =
            ArticleAdapter(
                callback,
                isWithSaveIcon
            )
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
        recyclerView.adapter =
            SourceAdapter(
                sourcesCallback
            )
    } else {
        (recyclerView.adapter as SourceAdapter).updateList(sources ?: emptyList())
    }
}

@BindingAdapter("newsError", "newsErrorCallback")
fun bindNewsError(
    parent: ViewGroup,
    newsError: NewsUIError?,
    newsErrorCallback : NewsErrorCallback
) {
    when (newsError) {
        is NewsUIError.NoSavedSources -> newsErrorCallback.noSavedSourcesError()
        is NewsUIError.Unknown -> showToast(parent.context, "Что-то пошло не так.")
        is NewsUIError.BadApiKey -> showToast(parent.context, "Проблемы с вашей подпиской.")
    }
}

@BindingAdapter("settingsError")
fun bindSettingsError(
    parent: ViewGroup,
    settingsError : SettingsError?
){
    when(settingsError){
        is SettingsError.BadApiKey -> showToast(parent.context, "Проблемы с вашей подпиской.")
        is SettingsError.Unknown -> showToast(parent.context, "Что-то пошло не так.")
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT)
        .show()
}