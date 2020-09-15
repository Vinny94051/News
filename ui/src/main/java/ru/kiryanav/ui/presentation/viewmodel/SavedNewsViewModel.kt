package ru.kiryanav.ui.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kiryanav.domain.NewsInteractor
import kotlinx.coroutines.launch
import ru.kiryanav.ui.mapper.toArticle
import ru.kiryanav.ui.mapper.toArticleUI
import ru.kiryanav.ui.model.ArticleUI
import vlnny.base.viewModel.BaseViewModel

class SavedNewsViewModel(
    private val newsInteractor: NewsInteractor,
    private val context: Context
) : BaseViewModel() {

    private val articlesMutableLiveData = MutableLiveData<List<ArticleUI>>()
    val articlesLiveData: LiveData<List<ArticleUI>>
        get() = articlesMutableLiveData

    fun deleteArticle(article: ArticleUI) {
        viewModelScope.launch {
            newsInteractor.deleteArticle(article.toArticle())
            getSavedArticles()
        }
    }

    fun getSavedArticles() {
        viewModelScope.launch {
            _isProgressVisible.value = true
            val result = newsInteractor.getSavedArticles()
            articlesMutableLiveData.value = result.map { article ->
                article.toArticleUI(context)
            }
            _isProgressVisible.value = false
        }
    }


}