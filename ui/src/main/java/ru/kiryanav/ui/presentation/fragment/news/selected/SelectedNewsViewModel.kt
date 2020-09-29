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
import vlnny.base.data.model.ResponseResult
import vlnny.base.data.model.isError
import vlnny.base.error.ui.Error
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
            if (newsInteractor.deleteArticle(article.toArticle()).isError()) {
                errorLiveData.value = Error.UNKNOWN
            }
        }
    }

    fun getSavedArticles() {
        viewModelScope.launch {
            isProgressVisibleLiveData.value = true

            when (val result = newsInteractor.getSavedArticles()) {
                is ResponseResult.Success -> {
                    articlesMutableLiveData.value = result.value.map { article ->
                        article.toArticleUI(context)
                    }
                }
                else -> errorLiveData.value = Error.UNKNOWN
            }

            isProgressVisibleLiveData.value = false
        }
    }


}