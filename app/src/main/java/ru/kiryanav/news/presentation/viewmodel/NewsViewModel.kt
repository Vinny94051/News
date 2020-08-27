package ru.kiryanav.news.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.kiryanav.news.Constants
import ru.kiryanav.news.R
import ru.kiryanav.news.domain.INewsInteractor
import ru.kiryanav.news.domain.model.ArticleUI
import ru.kiryanav.news.domain.model.NewsUIModel
import vlnny.base.rx.subscribeWithError
import vlnny.base.viewModel.BaseViewModel

class NewsViewModel : BaseViewModel(), KoinComponent {

    private val context : Context by inject()
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
        addDisposable(
            newsInteractor.getEverything(
                query, from, to, language
            )
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getProgressSingleTransformer())
                .subscribeWithError { news ->
                    _newsLiveData.value = news
                    _articlesLiveData.value = news.articles
                }
        )
    }

    fun loadMore(
        from: String = Constants.EMPTY_STRING,
        to: String = Constants.EMPTY_STRING,
        language: String = Constants.EMPTY_STRING
    ) {
        if (showSavedText.value != back) {
            updateUI(lastQuery)
            dayNumber++
            if (dayNumber < 7) {
                addDisposable(
                    newsInteractor.getEverything(
                        lastQuery, from, to, language, dayNumber
                    )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWithError { nextPage ->
                            _newsLiveData.value = nextPage
                            _articlesLiveData.value =
                                _articlesLiveData.value?.plus(nextPage.articles)
                            isLoadingMore.value = false
                        }
                )
            } else {
                isLoadingMore.value = false
            }
        } else {
            isLoadingMore.value = false
        }
    }


    fun saveArticle(item: ArticleUI) {
        addDisposable(
            newsInteractor.saveArticle(item)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isArticleSavedLiveData.value = true
                }, {
                    isArticleSavedLiveData.value = false
                })
        )
    }

    private fun getSavedArticles() {
        addDisposable(
            newsInteractor.getSavedArticles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWithError { saved ->
                    _articlesLiveData.value = saved
                }
        )
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