package ru.kiryanav.ui.presentation.fragment.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kiryanav.domain.NewsInteractor
import kotlinx.coroutines.launch
import ru.kiryanav.ui.mapper.toArticleSource
import ru.kiryanav.ui.mapper.toArticleSourceUI
import ru.kiryanav.ui.model.ArticleSourceUI
import ru.kiryanav.ui.utils.SingleLiveEvent
import vlnny.base.data.model.ResponseResult
import vlnny.base.data.model.isError
import vlnny.base.error.ui.Error
import vlnny.base.viewModel.BaseViewModel

class SettingsViewModel(
    private val newsInteractor: NewsInteractor
) : BaseViewModel() {

    private val _sourcesLiveData = MutableLiveData<List<ArticleSourceUI>>()
    val sourcesLiveData: LiveData<List<ArticleSourceUI>>
        get() = _sourcesLiveData

    private val _sourcesSavedNotify = SingleLiveEvent<Any>()
    val sourcesSavedNotify: LiveData<Any>
        get() = _sourcesSavedNotify

    fun loadSources() {
        viewModelScope.launch {
            when (val sources = newsInteractor.getSources()) {
                is ResponseResult.Success -> _sourcesLiveData.value =
                    sources.value.map { source -> source.toArticleSourceUI() }
                is ResponseResult.Error -> errorLiveData.value = Error.UNKNOWN
            }
        }
    }

    fun saveSources(sources: List<ArticleSourceUI>) {
        if (sources.isNotEmpty()) {
            viewModelScope.launch {
                if (newsInteractor.saveSources(
                        sources
                            .map { uiSource ->
                                uiSource.toArticleSource()
                            }
                    ).isError()
                ) {
                    errorLiveData.value = Error.UNKNOWN
                } else {
                    _sourcesSavedNotify.call()
                }
            }
        }
    }

    fun deleteSource(source: ArticleSourceUI) {
        viewModelScope.launch {
            if (newsInteractor.deleteSource(source.toArticleSource()).isError()) {
                errorLiveData.value = Error.UNKNOWN
            }
        }
    }


}