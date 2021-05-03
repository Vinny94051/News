package ru.kiryanav.ui.presentation.fragment.news.selected

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kiryanav.domain.NewsInteractor
import com.kiryanav.domain.error.NewsError
import kotlinx.coroutines.launch
import ru.kiryanav.ui.mapper.toArticle
import ru.kiryanav.ui.mapper.toArticleItemList
import ru.kiryanav.ui.model.ArticleItem
import ru.kiryanav.ui.presentation.base.BaseErrorViewModel
import ru.kiryanav.ui.presentation.fragment.news.NewsUIError
import vlnny.base.data.model.doOnError
import vlnny.base.data.model.doOnSuccess

class SelectedNewsViewModel(
    private val newsInteractor: NewsInteractor,
    private val context: Context
) : BaseErrorViewModel<NewsError, NewsUIError>() {

    private val articlesMutableLiveData = MutableLiveData<List<ArticleItem>>()
    val articlesLiveData: LiveData<List<ArticleItem>>
        get() = articlesMutableLiveData


    override fun defineErrorType(error: NewsError?): NewsUIError = when (error) {
        is NewsError.NoSavedSources -> NewsUIError.NoSavedSources
        is NewsError.BadApiKey -> NewsUIError.BadApiKey
        else -> NewsUIError.Unknown
    }

    fun remove(article: ArticleItem.ArticleUI) {
        viewModelScope.launch {
            newsInteractor.deleteArticle(article.toArticle())
                .doOnSuccess {
                    removeItemUI(article)
                }
                .doOnError {
                    errorLiveData.value = defineErrorType(it)
                }
        }
    }

    private fun removeItemUI(article: ArticleItem.ArticleUI) {
        val newList = articlesMutableLiveData.value?.toMutableList()
        val removedItemIndex = newList?.indexOf(article)

        removedItemIndex?.let { index ->

            if (newList[index - 1] is ArticleItem.DateHeader) {
                if (newList.size > index + 1) {
                    if (newList[index + 1] is ArticleItem.DateHeader) {
                        newList.removeAt(index)
                        newList.removeAt(index - 1)
                    } else {
                        newList.removeAt(index)
                    }
                } else {
                    for (i in 0 until 2) {
                        newList.removeAt(newList.size - 1)
                    }
                }
            } else {
                newList.removeAt(index)
            }
        }

        articlesMutableLiveData.value = newList
    }

    fun getSavedArticles() {
        viewModelScope.launch {
            isProgressVisibleLiveData.postValue(true)

            newsInteractor.getSavedArticles()
                .doOnSuccess { savedArticleWrapper ->
                    articlesMutableLiveData.postValue(
                        savedArticleWrapper
                            .toArticleItemList(context)
                    )
                }
                .doOnError {
                    errorLiveData.postValue(defineErrorType(it))
                }

            isProgressVisibleLiveData.postValue(false)
        }
    }
}