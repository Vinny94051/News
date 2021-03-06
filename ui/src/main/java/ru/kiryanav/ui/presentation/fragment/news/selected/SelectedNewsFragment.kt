package ru.kiryanav.ui.presentation.fragment.news.selected

import android.os.Bundle
import android.view.View
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.FragmentLocalNewsBinding
import ru.kiryanav.ui.model.ArticleItem
import ru.kiryanav.ui.presentation.fragment.news.callback.NewsErrorCallback
import ru.kiryanav.ui.presentation.fragment.news.callback.OnArticleItemClick
import ru.kiryanav.ui.presentation.fragment.news.sources.SourcesDialogFragment
import vlnny.base.ext.openLink
import vlnny.base.fragment.BaseBindableFragment

class SelectedNewsFragment : BaseBindableFragment<FragmentLocalNewsBinding>(),
    OnArticleItemClick, NewsErrorCallback,
    KoinComponent {

    private val savedNewsViewModel by viewModel<SelectedNewsViewModel>()

    override fun layoutId() = R.layout.fragment_local_news

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            callback = this@SelectedNewsFragment
            viewModel = savedNewsViewModel
        }
        loadSaved()
    }

    override fun onSaveItemClick(article: ArticleItem.ArticleUI, isSaved: Boolean) {
        if (!isSaved) {
            savedNewsViewModel.remove(article)
        }
    }

    override fun onItemClick(article: ArticleItem.ArticleUI) {
        context?.openLink(article.articleUrl)
    }

    override fun onShareItemClick(article: ArticleItem.ArticleUI) {
        startShareIntent(article.articleUrl, R.string.chosser_send_header)
    }

    private fun loadSaved() = savedNewsViewModel.getSavedArticles()

    override fun noSavedSourcesError() {
        showSnack(getString(R.string.default_error))
    }


    override fun unknownError() {
        showSnack(getString(R.string.error_something_went_wrong))
    }

    override fun badApiKeyError() {
        showSnack(getString(R.string.error_bad_api_key))
    }
}