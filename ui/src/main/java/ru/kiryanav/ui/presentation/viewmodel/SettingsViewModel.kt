package ru.kiryanav.ui.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kiryanav.domain.INewsInteractor
import com.kiryanav.domain.prefs.SourceManager
import kotlinx.coroutines.launch
import ru.kiryanav.ui.mapper.toArticleSourceUI
import ru.kiryanav.ui.model.ArticleSourceUI
import ru.kiryanav.ui.model.SavedSource
import vlnny.base.viewModel.BaseViewModel

class SettingsViewModel(
    private val newsInteractor: INewsInteractor,
    private val prefsManager: SourceManager
) : BaseViewModel() {

    private val _sourcesLiveData = MutableLiveData<List<ArticleSourceUI>>()
    val sourcesLiveData: LiveData<List<ArticleSourceUI>>
        get() = _sourcesLiveData

    private val _isSourceSaved = MutableLiveData<SavedSource>()
    val isSourceSaved: LiveData<SavedSource>
        get() = _isSourceSaved

    fun loadSourcesByLanguages(language: String) {
        viewModelScope.launch {
            _sourcesLiveData.value = newsInteractor
                .getSourcesByLanguage(language)
                .map { source ->
                    source.toArticleSourceUI()
                }
        }
    }

    fun setSource(item: ArticleSourceUI) {
        try {
            //Server sent 400 error, when {source.name} has space
            if (item.name!!.contains(" ")) {
                _isSourceSaved.value = SavedSource(false, item)
                return
            }
            prefsManager.saveSource(item.name)
            _isSourceSaved.value = SavedSource(true, item)
        } catch (ex: Throwable) {
            ex.printStackTrace()
            _isSourceSaved.value = SavedSource(false, item)
        }
    }


}