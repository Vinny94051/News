package ru.kiryanav.ui.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kiryanav.domain.INewsInteractor
import kotlinx.coroutines.launch
import ru.kiryanav.ui.mapper.toArticle
import ru.kiryanav.ui.model.ArticleUI
import vlnny.base.viewModel.BaseViewModel

open class BaseNewsViewModel(
    private val newsInteractor: INewsInteractor
) :
    BaseViewModel() {

    protected val articlesMutableLiveData = MutableLiveData<List<ArticleUI>>()
    val articlesLiveData: LiveData<List<ArticleUI>>
        get() = articlesMutableLiveData

    open fun removeArticle(article: ArticleUI) {
        viewModelScope.launch {
            newsInteractor.deleteArticle(article.toArticle())
        }
    }
}