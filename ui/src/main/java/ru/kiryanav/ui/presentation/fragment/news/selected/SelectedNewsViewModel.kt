package ru.kiryanav.ui.presentation.fragment.news.selected

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kiryanav.domain.NewsInteractor
import kotlinx.coroutines.launch
import ru.kiryanav.ui.mapper.toArticle
import ru.kiryanav.ui.mapper.toArticleUI
import ru.kiryanav.ui.model.ArticleItem
import vlnny.base.data.model.doOnError
import vlnny.base.data.model.doOnSuccess
import vlnny.base.viewModel.BaseViewModel

class SelectedNewsViewModel(
    private val newsInteractor: NewsInteractor,
    private val context: Context
) : BaseViewModel() {

    private val articlesMutableLiveData = MutableLiveData<List<ArticleItem>>()
    val articlesLiveData: LiveData<List<ArticleItem>>
        get() = articlesMutableLiveData

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
            isProgressVisibleLiveData.value = true

            newsInteractor.getSavedArticles()
                .doOnSuccess { savedArticleWrapper ->
                    articlesMutableLiveData.value = savedArticleWrapper.map { article ->
                        article.toArticleUI(context)
                    }
                }
                .doOnError {
                    errorLiveData.value = defineErrorType(it)
                }

            isProgressVisibleLiveData.value = false
        }
    }


}