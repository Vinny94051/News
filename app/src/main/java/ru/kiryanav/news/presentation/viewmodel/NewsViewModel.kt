package ru.kiryanav.news.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kiryanav.news.Constants
import ru.kiryanav.news.R
import ru.kiryanav.news.domain.INewsInteractor
import ru.kiryanav.news.domain.model.ArticleUI
import ru.kiryanav.news.domain.model.NewsUIModel
import vlnny.base.viewModel.BaseViewModel

class NewsViewModel(private val context: Context, private val newsInteractor: INewsInteractor) :
    BaseViewModel() {


    private val _newsLiveData = MutableLiveData<NewsUIModel>()
    val newsLiveData: LiveData<NewsUIModel>
        get() = _newsLiveData

    private val _articlesLiveData = MutableLiveData<List<ArticleUI>>()
    val articlesLiveData: LiveData<List<ArticleUI>>
        get() = _articlesLiveData

    private var dayNumber: Int = Constants.ZERO_INT
    private var showSaved = context.getString(R.string.show_saved)
    private val back = context.getString(R.string.back)
    var lastQuery: String = Constants.EMPTY_STRING
    var isLoadingMore = MutableLiveData(false)
    val isArticleSavedLiveData = MutableLiveData<Boolean>()
    val showSavedText = MutableLiveData(showSaved)


    fun loadEverythingNews(
        query: String,
        from: String = Constants.EMPTY_STRING,
        to: String = Constants.EMPTY_STRING,
        language: String = Constants.EMPTY_STRING
    ) {
        updateUI(query)
        dayNumber = Constants.ZERO_INT
        viewModelScope.launch {
            _isProgressVisible.value = true
            val news = newsInteractor.getEverything(
                query, from, to, language
            )
            _isProgressVisible.value = false
            _newsLiveData.value = news
            _articlesLiveData.value = news.articles
        }
    }

    fun loadMore(
        from: String = Constants.EMPTY_STRING,
        to: String = Constants.EMPTY_STRING,
        language: String = Constants.EMPTY_STRING
    ) {
        viewModelScope.launch {
            if (showSavedText.value != back) {
                updateUI(lastQuery)
                dayNumber++
                if (dayNumber < 7) {
                    val nextPage =
                        newsInteractor.getEverything(lastQuery, from, to, language, dayNumber)
                    _newsLiveData.value = nextPage
                    _articlesLiveData.value = _articlesLiveData.value?.plus(nextPage.articles)
                    isLoadingMore.value = false
                } else {
                    isLoadingMore.value = false
                }
            } else {
                isLoadingMore.value = false
            }
        }
    }


    fun saveArticle(item: ArticleUI) {
        viewModelScope.launch {
            newsInteractor.saveArticle(item)
            isArticleSavedLiveData.value = true
        }
    }

    private fun getSavedArticles() {
        viewModelScope.launch {
            val result = newsInteractor.getSavedArticles()
            _articlesLiveData.value = result
        }
    }

    fun showSaved() {
        if (showSavedText.value == showSaved) {
            showSavedText.value = back
            getSavedArticles()
        } else {
            showSavedText.value = showSaved
            loadEverythingNews(lastQuery)
        }
    }

    private fun updateUI(query: String) {
        showSavedText.value = showSaved
        lastQuery = query
    }
}