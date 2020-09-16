package ru.kiryanav.ui.presentation.fragment.news.selected

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.FragmentLocalNewsBinding
import ru.kiryanav.ui.model.ArticleUI
import ru.kiryanav.ui.presentation.ui.fragment.news.OnArticleItemClick
import vlnny.base.ext.openLink
import vlnny.base.fragment.BaseBindableFragment

class SelectedNewsFragment : BaseBindableFragment<FragmentLocalNewsBinding>(),
    OnArticleItemClick,
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

    override fun onLongClick(article: ArticleUI, itemView: View) {
        PopupMenu(requireContext(), itemView).apply {
            inflate(R.menu.item_article_popup_local)
            setOnMenuItemClickListener { item ->
                when (item?.itemId) {
                    R.id.remove -> {
                        savedNewsViewModel.deleteArticle(article)
                    }
                }
                true
            }
        }.show()
    }

    override fun onItemClick(article: ArticleUI) {
        context?.openLink(article.articleUrl)
    }

    private fun loadSaved() = savedNewsViewModel.getSavedArticles()
}