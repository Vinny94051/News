package ru.kiryanav.ui.presentation.fragment.news.current

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kiryanav.domain.NewsInteractor
import com.kiryanav.domain.error.Error
import com.kiryanav.domain.error.NewsError
import com.kiryanav.domain.error.SourceError
import com.kiryanav.domain.model.ArticleSource
import kotlinx.coroutines.launch
import ru.kiryanav.ui.R
import ru.kiryanav.ui.mapper.toArticle
import ru.kiryanav.ui.mapper.toArticleItemList
import ru.kiryanav.ui.model.ArticleItem
import ru.kiryanav.ui.presentation.base.BaseErrorViewModel
import ru.kiryanav.ui.presentation.fragment.news.NewsUIError
import vlnny.base.data.model.doOnError
import vlnny.base.data.model.doOnSuccess
import vlnny.base.ext.currentDate
import vlnny.base.ext.getDate
import vlnny.base.ext.toDate
import java.util.*


class NewsViewModel(
    private val context: Context,
    private val newsInteractor: NewsInteractor
) : BaseErrorViewModel<Error, NewsUIError>() {

    private val _newsLiveData = MutableLiveData<List<ArticleItem>>()
    val newsLiveData: LiveData<List<ArticleItem>>
        get() = _newsLiveData

    private val _totalNewsLiveData = MutableLiveData<String>()
    val totalNewsLiveData: LiveData<String>
        get() = _totalNewsLiveData

    private val _isLoadingMore = MutableLiveData(false)
    val isLoadingMore: LiveData<Boolean>
        get() = _isLoadingMore

    private var dayNumber: Long = DAY_NUMBER_DEFAULT_VALUE
    var lastQuery: String = ""


    override fun defineErrorType(error: Error?): NewsUIError =
        when (error) {
            is SourceError.BadApiKey -> NewsUIError.BadApiKey
            is NewsError.BadApiKey -> NewsUIError.BadApiKey
            is NewsError.NoSavedSources -> NewsUIError.NoSavedSources
            else -> NewsUIError.Unknown
        }


    fun removeArticle(article: ArticleItem.ArticleUI) {
        viewModelScope.launch {
            newsInteractor.deleteArticle(article.toArticle())
                .doOnError {
                    errorLiveData.value = defineErrorType(it)
                }
        }
    }

    fun loadNews(
        query: String? = null,
        from: Date? = null,
        to: Date? = null,
        language: String? = null
    ) {
        updateUI(query)
        dayNumber = DAY_NUMBER_DEFAULT_VALUE

        viewModelScope.launch {
            isProgressVisibleLiveData.postValue(true)

            newsInteractor.getSavedSources()
                .doOnSuccess { savedSources ->
                    updateNews(savedSources, query, currentDate.toDate(), getDate(1), language)
                }.doOnError {
                    errorLiveData.value = defineErrorType(it)
                }
        }
    }

    fun loadMore(
        language: String? = null
    ) {
        viewModelScope.launch {
            _isLoadingMore.postValue(true)
            newsInteractor.getSavedSources()
                .doOnSuccess { sources ->
                    loadMoreNews(sources, language)
                }.doOnError {
                    errorLiveData.value = defineErrorType(it)
                }
        }
    }

    fun saveArticle(item: ArticleItem.ArticleUI) {
        viewModelScope.launch {
            newsInteractor.saveArticle(item.toArticle())
                .doOnError {
                    errorLiveData.value = defineErrorType(it)
                }
        }
    }

    private fun updateUI(query: String?) {
        lastQuery = query.orEmpty()
    }

    private suspend fun loadMoreNews(sources: List<ArticleSource>, language: String?) {
        updateUI(lastQuery)
        dayNumber++

        if (dayNumber < MAX_DAYS_NUMBER) {

            newsInteractor.getNews(
                    lastQuery,
                    getDate(dayNumber - 1),
                    getDate(dayNumber),
                    sources, language
                )
                .doOnSuccess { nextPage ->

                    setTotalNews(nextPage.totalResult)
                    _newsLiveData.postValue(_newsLiveData.value?.plus(
                        nextPage.articles.toArticleItemList(context)
                    ))

                }.doOnError {
                    errorLiveData.postValue(defineErrorType(it))
                }

            _isLoadingMore.postValue(false)
        } else {
            _isLoadingMore.postValue(false)
        }
    }

    private suspend fun updateNews(
        savedSources: List<ArticleSource>,
        query: String?,
        from: Date?,
        to: Date?,
        language: String?
    ) {

        newsInteractor.getNews(query, from, to, savedSources, language)
            .doOnSuccess { news ->
                setTotalNews(news.totalResult)
                Log.e(javaClass.simpleName, news.toString())
                _newsLiveData.postValue(news.articles.toArticleItemList(context))
            }
            .doOnError {
                errorLiveData.value = defineErrorType(it)
            }

        isProgressVisibleLiveData.postValue(false)
    }

    private fun setTotalNews(totalResult: Int) {
        _totalNewsLiveData.postValue(
            context.getString(R.string.total_results)
                .format(totalResult.toString())
        )
    }


    companion object {
        const val MAX_DAYS_NUMBER = 8
        const val DAY_NUMBER_DEFAULT_VALUE = 1L
    }


}