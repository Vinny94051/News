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
import ru.kiryanav.ui.mapper.toArticleUI
import ru.kiryanav.ui.model.ArticleItem
import ru.kiryanav.ui.presentation.base.BaseErrorViewModel
import ru.kiryanav.ui.presentation.fragment.news.NewsUIError
import vlnny.base.data.model.doOnError
import vlnny.base.data.model.doOnSuccess
import vlnny.base.viewModel.BaseViewModel

class SelectedNewsViewModel(
    private val newsInteractor: NewsInteractor,
    private val context: Context
) : BaseErrorViewModel<NewsError, NewsUIError>() {

    private val articlesMutableLiveData = MutableLiveData<List<ArticleItem>>()
    val articlesLiveData: LiveData<List<ArticleItem>>
        get() = articlesMutableLiveData


    override fun defineErrorType(error: NewsError?): NewsUIError {
        TODO("Not yet implemented")
    }

    fun remove(article: ArticleItem.ArticleUI) {
        viewModelScope.launch {
            newsInteractor.deleteArticle(article.toArticle())
                .doOnError {
                    errorLiveData.value = defineErrorType(it)
                }
        }
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