package ru.kiryanav.ui.presentation.fragment.news.current

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiryanav.domain.worker.NewsUpdaterListener
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.scope.scope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.FragmentNewsBinding
import ru.kiryanav.ui.model.ArticleItem
import ru.kiryanav.ui.presentation.fragment.news.OnArticleItemClick
import ru.kiryanav.ui.presentation.worker.WorkerViewModel
import vlnny.base.ext.openLink
import vlnny.base.fragment.BaseBindableFragment


class NewsFragment : BaseBindableFragment<FragmentNewsBinding>(),
    OnArticleItemClick, KoinComponent {

    private val newsViewModel by viewModel<NewsViewModel>()
    private val workViewModel = WorkerViewModel
    private val newsUpdateListener: NewsUpdaterListener by inject()
    private val job = CoroutineScope(Job())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            this.viewModel = this@NewsFragment.newsViewModel
            this.workerViewModel = this@NewsFragment.workViewModel
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

    override fun initViews() {
        super.initViews()
        initSearchView()
        initRecycler()
        initSettings()
        initPullToRefresh()
    }

    override fun initViewModel() {
        super.initViewModel()
        job.launch {
            newsUpdateListener.listener.collect { isUpdate ->
                if (isUpdate) {
                    loadNews()
                }
            }
        }
    }

    private fun initSettings() {
        more.setOnClickListener {
            createAndShowPopup(more, R.menu.more_popup,
                PopupMenu.OnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.settings -> findNavController()
                            .navigate(R.id.action_newsFragment_to_settingsFragment)
                        R.id.savedNews -> findNavController()
                            .navigate(R.id.action_newsFragment_to_selectedNewsFragment)
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
        query: String? = null
    ) =
        newsViewModel.loadNews(query)

    private fun createAndShowPopup(
        itemView: View,
        @MenuRes menuId: Int,
        menuItemClickListener: PopupMenu.OnMenuItemClickListener
    ) =
        PopupMenu(requireContext(), itemView).apply {
            inflate(menuId)
            setOnMenuItemClickListener(menuItemClickListener)
        }.show()

    override fun onCheckBoxClick(article: ArticleItem.ArticleUI, isSave: Boolean) {
        if (isSave) {
            newsViewModel.saveArticle(article)
        } else {
            newsViewModel.removeArticle(article)
        }
    }

    override fun onItemClick(article: ArticleItem.ArticleUI) {
        context?.openLink(article.articleUrl)
    }

    override fun onShareItemClick(article: ArticleItem.ArticleUI) {
        startShareIntent(article.articleUrl, R.string.chosser_send_header)
    }

}