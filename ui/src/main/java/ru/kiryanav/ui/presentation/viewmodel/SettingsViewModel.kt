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

    fun loadSourcesByLanguages(language: String) {
        viewModelScope.launch {
            val byLangSources =
                newsInteractor
                    .getSourcesByLanguage(language)
                    .map { source ->
                        source.toArticleSourceUI()
                    }

            val savedSources = newsInteractor.getSavedSources().map { source ->
                source.toArticleSourceUI(true)
            }
            if (savedSources.isNotEmpty()) {
                val resultSources = compareAndChoose(byLangSources, savedSources)
                _sourcesLiveData.value = resultSources
            } else {
                _sourcesLiveData.value = byLangSources
            }
        }
    }

    private fun compareAndChoose(
        l1: List<ArticleSourceUI>,
        l2: List<ArticleSourceUI>
    ): List<ArticleSourceUI> {
        val tmpList: MutableList<ArticleSourceUI> = l1.toMutableList()
        for (i in l1.indices) {
            for (k in l2.indices) {
                if (l1[i].name == l2[k].name) {
                    tmpList[i] = l2[k]
                    break
                }
            }
        }

        return tmpList.filter {
            !it.name!!.contains(" ")
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