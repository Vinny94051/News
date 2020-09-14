package ru.kiryanav.ui.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kiryanav.domain.INewsInteractor
import kotlinx.coroutines.launch
import ru.kiryanav.ui.mapper.toArticleSource
import ru.kiryanav.ui.mapper.toArticleSourceUI
import ru.kiryanav.ui.model.ArticleSourceUI
import vlnny.base.viewModel.BaseViewModel

class SettingsViewModel(
    private val newsInteractor: INewsInteractor
) : BaseViewModel() {

    private val _sourcesLiveData = MutableLiveData<List<ArticleSourceUI>>()
    val sourcesLiveData: LiveData<List<ArticleSourceUI>>
        get() = _sourcesLiveData

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
            }
        }
    }

    fun deleteSource(source: ArticleSourceUI) {
        viewModelScope.launch {
            newsInteractor.deleteSource(source.toArticleSource())
        }
    }

}