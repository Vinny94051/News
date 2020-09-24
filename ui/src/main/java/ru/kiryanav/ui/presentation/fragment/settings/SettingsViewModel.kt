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
            _sourcesLiveData.value = newsInteractor.getSources()
                .map { source ->
                    source.toArticleSourceUI()
                }
        }
    }

    fun saveSources(sources: List<ArticleSourceUI>) {
        if (sources.isNotEmpty()) {
            viewModelScope.launch {
                newsInteractor.saveSources(sources.map { it.toArticleSource() })
            }.invokeOnCompletion {
                it?.printStackTrace()
                _sourcesSavedNotify.call()
            }

        }
    }

    fun deleteSource(source: ArticleSourceUI) {
        viewModelScope.launch {
            newsInteractor.deleteSource(source.toArticleSource())
        }
    }

    fun createNotifyWork(){

    }

    fun cancelNotifyWork(){

    }

}