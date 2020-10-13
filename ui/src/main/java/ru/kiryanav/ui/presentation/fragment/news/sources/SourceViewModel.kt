package ru.kiryanav.ui.presentation.fragment.news.sources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kiryanav.domain.NewsInteractor
import com.kiryanav.domain.error.SourceError
import kotlinx.coroutines.launch
import ru.kiryanav.ui.mapper.toArticleSource
import ru.kiryanav.ui.mapper.toArticleSourceUI
import ru.kiryanav.ui.model.ArticleSourceUI
import ru.kiryanav.ui.presentation.base.BaseErrorViewModel
import ru.kiryanav.ui.presentation.fragment.news.NewsUIError
import ru.kiryanav.ui.utils.SingleLiveEvent
import vlnny.base.data.model.doOnError
import vlnny.base.data.model.doOnSuccess


class SourceViewModel(private val newsInteractor: NewsInteractor) :
    BaseErrorViewModel<SourceError, NewsUIError>() {


    private val _sourcesLiveData = MutableLiveData<List<ArticleSourceUI>>()
    val sourcesLiveData: LiveData<List<ArticleSourceUI>>
        get() = _sourcesLiveData

    private val sourceSavedEvent = SingleLiveEvent<Nothing>()
    val sourceSaved: LiveData<Nothing>
        get() = sourceSavedEvent


    override fun defineErrorType(error: SourceError?): NewsUIError {
        return when (error) {
            SourceError.BadApiKey -> NewsUIError.BadApiKey
            else -> NewsUIError.Unknown
        }
    }

    fun loadSources() {
        viewModelScope.launch {
            newsInteractor.getSources()
                .doOnSuccess { sources ->
                    _sourcesLiveData.value = sources.map { wrappedSource ->
                        wrappedSource.toArticleSourceUI()
                    }
                }
                .doOnError {
                    defineErrorType(it)
                }
        }
    }

    fun saveSource(source: ArticleSourceUI) {
        viewModelScope.launch {
            newsInteractor.saveSources(listOf(source.toArticleSource()))
                .doOnSuccess {
                    sourceSavedEvent.call()
                }
                .doOnError {
                    defineErrorType(it)
                }
        }
    }

}