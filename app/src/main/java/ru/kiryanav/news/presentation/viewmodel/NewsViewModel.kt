package ru.kiryanav.news.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.kiryanav.news.App
import ru.kiryanav.news.domain.INewsInteractor
import ru.kiryanav.news.domain.model.NewsUIModel
import vlnny.base.rx.subscribeWithError
import vlnny.base.viewModel.BaseViewModel
import javax.inject.Inject

class NewsViewModel : BaseViewModel() {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var newsInteractor: INewsInteractor

    private val _newsLiveData = MutableLiveData<NewsUIModel>()
    val newsLiveData: LiveData<NewsUIModel>
        get() = _newsLiveData

    fun loadEverythingNews(query: String, from: String, to: String, language: String) {
        addDisposable(
            newsInteractor.getEverything(
                query, from, to, language
            )
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getProgressSingleTransformer())
                .subscribeWithError { news ->
                    _newsLiveData.value = news
                }
        )
    }
}