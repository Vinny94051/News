package ru.kiryanav.news.presentation.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news.*
import ru.kiryanav.news.Constants
import ru.kiryanav.news.R
import ru.kiryanav.news.databinding.FragmentNewsBinding
import ru.kiryanav.news.domain.model.ArticleUI
import ru.kiryanav.news.presentation.view.list.OnArticleItemClick
import ru.kiryanav.news.presentation.viewmodel.NewsViewModel
import vlnny.base.fragment.BaseBindableFragment
import vlnny.base.fragment.BaseFragmentCompanion


class NewsFragment : BaseBindableFragment<FragmentNewsBinding>(), OnArticleItemClick {

    companion object : BaseFragmentCompanion<NewsFragment>() {
        override fun newInstance(): NewsFragment = NewsFragment()
    }

    private lateinit var viewModel: NewsViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            this.viewModel = this@NewsFragment.viewModel
            this.callback = this@NewsFragment
            executePendingBindings()
            loadNews(getString(R.string.default_search))
        }
    }

    override fun layoutId() = R.layout.fragment_news

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
            .apply {
                isArticleSavedLiveData.observe(this@NewsFragment, Observer { isSaved ->
                    if (isSaved) {
                        showSnack(getString(R.string.article_saved))
                    } else {
                        showSnack(getString(R.string.error_saved))
                    }
                })
            }
    }

    override fun initViews() {
        super.initViews()
        initSearchView()
        initRecycler()
        initPullToRefresh()
    }

    private fun initPullToRefresh() {
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                context!!,
                R.color.colorPrimary
            )
        )
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE)

        swipeRefreshLayout.setOnRefreshListener {
            loadNews(viewModel.lastQuery)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initRecycler() {
        newsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = newsRecycler.layoutManager

                val totalItemCount = layoutManager?.itemCount ?: Constants.EMPTY_INT
                val lastVisibleItem =
                    (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!viewModel.isLoadingMore.value!! && totalItemCount <= (lastVisibleItem + 1)) {
                    viewModel.isLoadingMore.value = true
                    viewModel.loadMore()
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

            override fun onQueryTextChange(newText: String?) = true
        })
    }

    override fun popupSave(item: ArticleUI) =
        viewModel.saveArticle(item)

    private fun loadNews(
        query: String,
        from: String = Constants.EMPTY_STRING,
        to: String = Constants.EMPTY_STRING,
        language: String = Constants.EMPTY_STRING
    ) =
        viewModel.loadEverythingNews(query, from, to, language)


}