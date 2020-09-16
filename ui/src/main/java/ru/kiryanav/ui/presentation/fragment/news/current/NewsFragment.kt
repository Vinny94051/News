package ru.kiryanav.ui.presentation.fragment.news.current

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.FragmentNewsBinding
import ru.kiryanav.ui.model.ArticleUI
import ru.kiryanav.ui.presentation.fragment.news.OnArticleItemClick
import vlnny.base.ext.openLink
import vlnny.base.fragment.BaseBindableFragment


class NewsFragment : BaseBindableFragment<FragmentNewsBinding>(),
    OnArticleItemClick,
    KoinComponent {

    private val newsViewModel by viewModel<NewsViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            this.viewModel = this@NewsFragment.newsViewModel
            this.callback = this@NewsFragment
            executePendingBindings()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchTheme = searchView.query
        if (searchTheme.isNullOrEmpty()) {
            loadNews()
        } else {
            loadNews(searchTheme.toString())
        }
    }

    override fun layoutId() = R.layout.fragment_news

    override fun initViewModel() {
        super.initViewModel()
        newsViewModel.isArticleSavedLiveData.observe(this, Observer {
            showSnack(requireContext().getString(R.string.article_saved))
        })
    }

    override fun initViews() {
        super.initViews()
        initSearchView()
        initRecycler()
        initSettings()
        initPullToRefresh()
    }

    private fun initSettings() {
        more.setOnClickListener {
            createAndShowPopup(more, R.menu.more_popup,
                PopupMenu.OnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.settings -> findNavController()
                            .navigate(R.id.action_newsFragment_to_settingsFragment)
                        R.id.savedNews -> findNavController()
                            .navigate(R.id.action_newsFragment_to_savedNewsFragment)
                    }
                    true
                })
        }
    }

    private fun initPullToRefresh() {
        swipeRefreshLayout.apply {
            setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPrimary
                )
            )
            setColorSchemeColors(Color.WHITE)
            setOnRefreshListener {
                loadNews(newsViewModel.lastQuery)
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun initRecycler() {
        newsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = newsRecycler.layoutManager

                val totalItemCount: Int = layoutManager?.itemCount ?: 0
                val lastVisibleItem =
                    (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (
                    !newsViewModel.isLoadingMore.value!! &&
                    totalItemCount <= (lastVisibleItem + 1)
                ) {
                    newsViewModel.loadMore()
                }
            }
        })
    }

    private fun initSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { q ->
                    loadNews(q)
                    hideKeyboard(searchView)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    loadNews()
                    searchView.isIconified = true
                }
                return true
            }
        })
    }


    private fun loadNews(
        query: String? = null,
        from: String? = null,
        to: String? = null,
        language: String? = null
    ) =
        newsViewModel.loadNews(query, from, to, language)

    override fun onLongClick(article: ArticleUI, itemView: View) {
        if (!article.isSaved) {
            createAndShowPopup(
                itemView,
                R.menu.item_article_popup_remote,
                PopupMenu.OnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.save -> {
                            newsViewModel.saveArticle(article)
                        }
                    }
                    true
                }
            )
        } else {
            createAndShowPopup(itemView,
                R.menu.item_article_popup_local,
                PopupMenu.OnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.remove -> {
                            newsViewModel.removeArticle(article)
                        }
                    }
                    true
                }
            )
        }
    }

    private fun createAndShowPopup(
        itemView: View,
        @MenuRes menuId: Int,
        menuItemClickListener: PopupMenu.OnMenuItemClickListener
    ) =
        PopupMenu(requireContext(), itemView).apply {
            inflate(menuId)
            setOnMenuItemClickListener(menuItemClickListener)
        }.show()

    override fun onItemClick(article: ArticleUI) {
        context?.openLink(article.articleUrl)
    }

}