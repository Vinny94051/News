package ru.kiryanav.news.presentation.viewmodel

import android.text.BoringLayout
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.kiryanav.news.App
import ru.kiryanav.news.Constants
import ru.kiryanav.news.domain.INewsInteractor
import ru.kiryanav.news.domain.model.ArticleUI
import ru.kiryanav.news.domain.model.NewsUIModel
import vlnny.base.rx.subscribeWithError
import vlnny.base.viewModel.BaseViewModel
import javax.inject.Inject

class NewsViewModel : BaseViewModel() {

    init {
        App.appComponent.inject(this)
    }

    companion object {
        private const val SHOW_SAVED = "Show saved"
        private const val BACK = "Back"
    }

    @Inject
    lateinit var newsInteractor: INewsInteractor

    private val _newsLiveData = MutableLiveData<NewsUIModel>()
    val newsLiveData: LiveData<NewsUIModel>
        get() = _newsLiveData

    private val _savedArticlesLiveData = MutableLiveData<List<ArticleUI>>()
    val articlesLiveData: LiveData<List<ArticleUI>>
        get() = _savedArticlesLiveData

    private var lastQuery: String = Constants.EMPTY_STRING
    val isArticleSavedLiveData = MutableLiveData<Boolean>()
    val showSavedText = MutableLiveData<String>(SHOW_SAVED)


    fun loadEverythingNews(
        query: String,
        from: String = Constants.EMPTY_STRING,
        to: String = Constants.EMPTY_STRING,
        language: String = Constants.EMPTY_STRING
    ) {
        showSavedText.value = SHOW_SAVED
        lastQuery = query
        addDisposable(
            newsInteractor.getEverything(
                query, from, to, language
            )
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getProgressSingleTransformer())
                .subscribeWithError { news ->
                    _newsLiveData.value = news
                    _savedArticlesLiveData.value = news.articles
                }
        )
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
                    _savedArticlesLiveData.value = saved
                }
        )
    }

    fun showSaved() {
        if (showSavedText.value == SHOW_SAVED) {
            showSavedText.value = BACK
            getSavedArticles()
        } else {
            showSavedText.value = SHOW_SAVED
            loadEverythingNews(lastQuery)
        }
    }
}