package ru.kiryanav.ui.presentation.viewmodel

import android.content.Context
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
import ru.kiryanav.ui.utils.SingleLiveEvent
import java.time.LocalDateTime


class NewsViewModel(private val context: Context, private val newsInteractor: INewsInteractor) :
    BaseNewsViewModel(newsInteractor) {

    private val _totalNewsLiveData = MutableLiveData<String>()
    val totalNewsLiveData: LiveData<String>
        get() = _totalNewsLiveData

    private val _isLoadingMore = MutableLiveData(false)
    val isLoadingMore: LiveData<Boolean>
        get() = _isLoadingMore

    private val _isArticleSavedLiveData = SingleLiveEvent<Boolean>()
    val isArticleSavedLiveData: LiveData<Boolean>
        get() = _isArticleSavedLiveData

    private var dayNumber: Int = Constants.ZERO_INT
    var lastQuery: String = Constants.EMPTY_STRING

    override fun removeArticle(article: ArticleUI) {
        super.removeArticle(article)
        loadNews()
    }

    fun loadNews(
        query: String = Constants.EMPTY_STRING,
        from: String = Constants.EMPTY_STRING,
        to: String = Constants.EMPTY_STRING,
        language: String = Constants.EMPTY_STRING
    ) {
        updateUI(query)
        dayNumber = Constants.ZERO_INT
        viewModelScope.launch {
            _isProgressVisible.value = true

            val sources = newsInteractor.getSavedSources()

            val news = newsInteractor.getNews(
                query, from, to, sources, language
            )
            _isProgressVisible.value = false
            _totalNewsLiveData.value =
                context.getString(R.string.total_results).format(news.resultNumber.toString())
            articlesMutableLiveData.value = news.articles.map { article -> article.toArticleUI(context) }
        }
    }

    fun loadMore(
        language: String = Constants.EMPTY_STRING
    ) {
        viewModelScope.launch {
            _isLoadingMore.value = true

            val sources = newsInteractor.getSavedSources()

            updateUI(lastQuery)
            dayNumber++

            if (dayNumber < WEEK_DAYS_NUMBER) {

                val nextPage = newsInteractor
                    .getNews(
                        lastQuery,
                        getDate(dayNumber - 1),
                        getDate(dayNumber),
                        sources,
                        language
                    )

                _totalNewsLiveData.value = context.getString(R.string.total_results)
                    .format(nextPage.resultNumber.toString())
                articlesMutableLiveData.value = articlesMutableLiveData.value
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
        }
    }

    private fun getDate(dayNumber: Int): String =
        LocalDateTime.now().minusDays(dayNumber.toLong()).toString()


    fun saveArticle(item: ArticleUI) {
        viewModelScope.launch {
            newsInteractor.saveArticle(item.toArticle())
            _isArticleSavedLiveData.call()
            loadNews()
        }
    }

    private fun updateUI(query: String) {
        lastQuery = query
    }

    companion object {
        private const val WEEK_DAYS_NUMBER = 7
    }


}