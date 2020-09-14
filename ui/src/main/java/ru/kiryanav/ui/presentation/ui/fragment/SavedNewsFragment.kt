package ru.kiryanav.ui.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.FragmentLocalNewsBinding
import ru.kiryanav.ui.model.ArticleUI
import ru.kiryanav.ui.presentation.ui.list.OnArticleItemClick
import ru.kiryanav.ui.presentation.viewmodel.SavedNewsViewModel
import vlnny.base.ext.openLink
import vlnny.base.fragment.BaseBindableFragment

class SavedNewsFragment : BaseBindableFragment<FragmentLocalNewsBinding>(), OnArticleItemClick,
    KoinComponent {

    private val savedNewsViewModel by viewModel<SavedNewsViewModel>()

    override fun layoutId() = R.layout.fragment_local_news

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            callback = this@SavedNewsFragment
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
                        savedNewsViewModel.removeArticle(article)
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