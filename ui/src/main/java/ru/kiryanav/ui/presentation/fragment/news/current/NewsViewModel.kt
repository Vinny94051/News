package ru.kiryanav.ui.presentation.fragment.news.current

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kiryanav.ui.R
import com.kiryanav.domain.NewsInteractor
import com.kiryanav.domain.model.ArticleSource
import ru.kiryanav.ui.mapper.toArticle
import ru.kiryanav.ui.mapper.toArticleItemList
import ru.kiryanav.ui.model.ArticleItem
import vlnny.base.data.model.*
import vlnny.base.error.ui.Error
import vlnny.base.viewModel.BaseViewModel
import java.time.LocalDateTime


class NewsViewModel(
    private val context: Context,
    private val newsInteractor: NewsInteractor
) :
    BaseViewModel() {

    private val _newsLiveData = MutableLiveData<List<ArticleItem>>()
    val newsLiveData: LiveData<List<ArticleItem>>
        get() = _newsLiveData

    private val _totalNewsLiveData = MutableLiveData<String>()
    val totalNewsLiveData: LiveData<String>
        get() = _totalNewsLiveData

    private val _isLoadingMore = MutableLiveData(false)
    val isLoadingMore: LiveData<Boolean>
        get() = _isLoadingMore

    private var dayNumber: Int = DAY_NUMBER_DEFAULT_VALUE
    var lastQuery: String = ""

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
        from: String? = null,
        to: String? = null,
        language: String? = null
    ) {
        updateUI(query)
        dayNumber = DAY_NUMBER_DEFAULT_VALUE

        viewModelScope.launch {
            isProgressVisibleLiveData.value = true

            newsInteractor.getSavedSources()
                .doOnSuccess { savedSources ->
                    updateNews(savedSources, query, from, to, language)
                }.doOnError {
                    errorLiveData.value = defineErrorType(it)
                }
        }
    }

    fun loadMore(
        language: String? = null
    ) {
        viewModelScope.launch {
            _isLoadingMore.value = true

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

        if (dayNumber < WEEK_DAYS_NUMBER) {

            newsInteractor
                .getNews(
                    lastQuery,
                    getDate(dayNumber - 1),
                    getDate(dayNumber),
                    sources,
                    language
                ).doOnSuccess { nextPage ->
                    setTotalNews(nextPage.totalResult)
                    _newsLiveData.value = _newsLiveData.value
                            ?.plus(nextPage.articles.toArticleItemList(context))

                }.doOnError {
                    errorLiveData.value = defineErrorType(it)
                }

            _isLoadingMore.value = false
        } else {
            _isLoadingMore.value = false
        }
    }

    private suspend fun updateNews(
        savedSources: List<ArticleSource>,
        query: String?,
        from: String?,
        to: String?,
        language: String?
    ) {

        newsInteractor.getNews(query, from, to, savedSources, language)
            .doOnSuccess { news ->
                setTotalNews(news.totalResult)
                _newsLiveData.value = news.articles.toArticleItemList(context)
            }
            .doOnError {
                errorLiveData.value = defineErrorType(it)
            }

        isProgressVisibleLiveData.value = false
    }

    private fun setTotalNews(totalResult : Int){
        _totalNewsLiveData.value = context.getString(R.string.total_results)
            .format(totalResult.toString())
    }

    private fun getDate(dayNumber: Int): String =
        LocalDateTime.now().minusDays(dayNumber.toLong()).toString()

    companion object {
        private const val WEEK_DAYS_NUMBER = 8
        private const val DAY_NUMBER_DEFAULT_VALUE = 1
    }
}