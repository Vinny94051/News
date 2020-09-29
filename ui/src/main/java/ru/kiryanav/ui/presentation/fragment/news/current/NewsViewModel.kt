package ru.kiryanav.ui.presentation.fragment.news.current

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kiryanav.ui.R
import com.kiryanav.domain.NewsInteractor
import com.kiryanav.domain.model.NewsWrapper
import ru.kiryanav.ui.mapper.toArticle
import ru.kiryanav.ui.mapper.toArticleItemList
import ru.kiryanav.ui.model.ArticleItem
import ru.kiryanav.ui.presentation.worker.WorkerViewModel
import vlnny.base.data.model.ResponseResult
import vlnny.base.error.ui.Error
import vlnny.base.viewModel.BaseViewModel
import java.time.LocalDateTime


class NewsViewModel(
    private val context: Context,
    private val newsInteractor: NewsInteractor
) :
    BaseViewModel() {


//    private val articlesMutableLiveData = MutableLiveData<List<ArticleItem>>()
//    val articlesLiveData: LiveData<List<ArticleItem>>
//        get() = articlesMutableLiveData

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

            when (val savedSources = newsInteractor.getSavedSources()) {
                is ResponseResult.Success -> {
                    newsInteractor.getNews(
                        query, from, to, savedSources.value, language
                    )
                        .apply {

                            when (this) {
                                is ResponseResult.Success -> {
                                    _totalNewsLiveData.value =
                                        context.getString(R.string.total_results)
                                            .format(value.totalResult.toString())

                                    WorkerViewModel.newsLiveData.value =
                                        value.articles.toArticleItemList(context)
                                }

                                is ResponseResult.Error -> errorLiveData.value = Error.UNKNOWN

                            }
                            isProgressVisibleLiveData.value = false

                        }
                }

                is ResponseResult.Error -> errorLiveData.value = Error.UNKNOWN
            }
        }
    }

    fun loadMore(
        language: String? = null
    ) {
        viewModelScope.launch {
            _isLoadingMore.value = true
            when (val sources = newsInteractor.getSavedSources()) {
                is ResponseResult.Success -> {
                    updateUI(lastQuery)
                    dayNumber++

                    if (dayNumber < WEEK_DAYS_NUMBER) {

                        val nextPage = newsInteractor
                            .getNews(
                                lastQuery,
                                getDate(dayNumber - 1),
                                getDate(dayNumber),
                                sources.value,
                                language
                            )

                        when (nextPage) {
                            is ResponseResult.Success -> {
                                _totalNewsLiveData.value = context.getString(R.string.total_results)
                                    .format(nextPage.value.totalResult.toString())
                                WorkerViewModel.newsLiveData.value =
                                    WorkerViewModel.newsLiveData.value
                                        ?.plus(
                                            nextPage.value.articles
                                                .toArticleItemList(context)
                                        )
                            }
                            is ResponseResult.Error -> errorLiveData.value = Error.UNKNOWN
                        }
                        _isLoadingMore.value = false
                    } else {
                        _isLoadingMore.value = false
                    }
                }
                is ResponseResult.Error -> errorLiveData.value = Error.UNKNOWN
            }
        }
    }

    fun saveArticle(item: ArticleItem.ArticleUI) {
        viewModelScope.launch {
            newsInteractor.saveArticle(item.toArticle())
        }
    }

    private fun updateUI(query: String?) {
        lastQuery = query.orEmpty()
    }

    private fun getDate(dayNumber: Int): String =
        LocalDateTime.now().minusDays(dayNumber.toLong()).toString()

    companion object {
        private const val WEEK_DAYS_NUMBER = 8
        private const val DAY_NUMBER_DEFAULT_VALUE = 1
    }
}