package ru.kiryanav.ui.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kiryanav.ui.Constants
import ru.kiryanav.ui.R
import com.kiryanav.domain.INewsInteractor
import ru.kiryanav.ui.mapper.toArticle
import ru.kiryanav.ui.mapper.toArticleUI
import ru.kiryanav.ui.model.ArticleUI
import vlnny.base.viewModel.BaseViewModel
import java.time.LocalDateTime
import java.util.*

class NewsViewModel(private val context: Context, private val newsInteractor: INewsInteractor) :
    BaseViewModel() {


    private var dayNumber: Int = Constants.ZERO_INT
    private var showSaved = context.getString(R.string.show_saved)
    private val back = context.getString(R.string.back)
    var lastQuery: String = Constants.EMPTY_STRING


    private val _totalNewsLiveData = MutableLiveData<String>()
    val totalNewsLiveData: LiveData<String>
        get() = _totalNewsLiveData

    private val _articlesLiveData = MutableLiveData<List<ArticleUI>>()
    val articlesLiveData: LiveData<List<ArticleUI>>
        get() = _articlesLiveData

    private val _isLoadingMore = MutableLiveData(false)
    val isLoadingMore: LiveData<Boolean>
        get() = _isLoadingMore

    private val _isArticleSavedLiveData = MutableLiveData<Boolean>()
    val isArticleSavedLiveData: LiveData<Boolean>
        get() = _isArticleSavedLiveData

    private val _showSavedText = MutableLiveData(showSaved)
    val showSavedText: LiveData<String>
        get() = _showSavedText


    fun loadEverythingNews(
        query: String = Constants.EMPTY_STRING,
        from: String = Constants.EMPTY_STRING,
        to: String = Constants.EMPTY_STRING,
        language: String = Constants.EMPTY_STRING
    ) {
        updateUI(query)
        dayNumber = Constants.ZERO_INT
        viewModelScope.launch {
            _isProgressVisible.value = true
            val news = newsInteractor.getNews(
                query, from, to, language
            )
            _isProgressVisible.value = false
            _totalNewsLiveData.value =
                context.getString(R.string.total_results).format(news.resultNumber.toString())
            _articlesLiveData.value = news.articles.map { article -> article.toArticleUI(context) }
        }
    }

    fun loadMore(
        language: String = Constants.EMPTY_STRING
    ) {
        viewModelScope.launch {
            _isLoadingMore.value = true

            if (showSavedText.value != back) {
                updateUI(lastQuery)
                dayNumber++

                if (dayNumber < WEEK_DAYS_NUMBER) {

                    val nextPage = newsInteractor
                        .getNews(
                            lastQuery,
                            getDate(dayNumber - 1),
                            getDate(dayNumber),
                            language
                        )

                    _totalNewsLiveData.value = context.getString(R.string.total_results)
                        .format(nextPage.resultNumber.toString())
                    _articlesLiveData.value = _articlesLiveData.value
                        ?.plus(
                            nextPage.articles
                                .map { article ->
                                    article.toArticleUI(context)
                                }
                        )
                    _isLoadingMore.value = false
                } else {
                    _isLoadingMore.value = false
                }
            } else {
                _isLoadingMore.value = false
            }
        }
    }

    private fun getDate(dayNumber: Int): String =
        LocalDateTime.now().minusDays(dayNumber.toLong()).toString()


    fun saveArticle(item: ArticleUI) {
        viewModelScope.launch {
            newsInteractor.saveArticle(item.toArticle())
            _isArticleSavedLiveData.value = true
        }
    }

    private fun getSavedArticles() {
        viewModelScope.launch {
            val result = newsInteractor.getSavedArticles()
            _articlesLiveData.value = result.map { article ->
                article.toArticleUI(context)
            }
        }
    }

    fun showSaved() {
        if (showSavedText.value == showSaved) {
            _showSavedText.value = back
            getSavedArticles()
        } else {
            _showSavedText.value = showSaved
            loadEverythingNews(lastQuery)
        }
    }

    private fun updateUI(query: String) {
        _showSavedText.value = showSaved
        lastQuery = query
    }

    companion object {
        private const val WEEK_DAYS_NUMBER = 7
    }
}