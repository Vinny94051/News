package ru.kiryanav.ui.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.kiryanav.domain.INewsInteractor
import kotlinx.coroutines.launch
import ru.kiryanav.ui.mapper.toArticleUI
import ru.kiryanav.ui.model.ArticleUI

class SavedNewsViewModel(
    private val newsInteractor: INewsInteractor,
    private val context: Context
) : BaseNewsViewModel(newsInteractor) {

    override fun removeArticle(article: ArticleUI) {
        super.removeArticle(article)
        getSavedArticles()
    }

    fun getSavedArticles() {
        viewModelScope.launch {
            _isProgressVisible.value = true
            val result = newsInteractor.getSavedArticles(false)
            articlesMutableLiveData.value = result.map { article ->
                article.toArticleUI(context)
            }
            _isProgressVisible.value = false
        }
    }


}