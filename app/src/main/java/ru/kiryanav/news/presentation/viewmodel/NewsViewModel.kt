package ru.kiryanav.news.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.kiryanav.news.Constants
import ru.kiryanav.news.R
import ru.kiryanav.news.domain.INewsInteractor
import ru.kiryanav.news.domain.model.ArticleUI
import ru.kiryanav.news.domain.model.NewsUIModel
import vlnny.base.viewModel.BaseViewModel

class NewsViewModel : BaseViewModel(), KoinComponent {


    private val context: Context by inject()
    private val newsInteractor: INewsInteractor by inject()

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
            val deffered = async {
                newsInteractor.getEverything(
                    query, from, to, language
                )
            }
            val news = deffered.await()
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
                    val deffered = async {
                        newsInteractor.getEverything(lastQuery, from, to, language, dayNumber)
                    }
                    val nextPage = deffered.await()
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
            val isSaved = async {
                newsInteractor.saveArticle(item)
            }
            isSaved.await()
            isArticleSavedLiveData.value = true
        }
    }

    private fun getSavedArticles() {
        viewModelScope.launch {
            val deffered = async {
                newsInteractor.getSavedArticles()
            }
            val result = deffered.await()
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