package ru.kiryanav.ui.presentation.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import ru.kiryanav.ui.Constants
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.FragmentNewsBinding
import ru.kiryanav.ui.model.ArticleUI
import ru.kiryanav.ui.presentation.ui.list.OnArticleItemClick
import ru.kiryanav.ui.presentation.ui.view.AlertDialogHelper
import ru.kiryanav.ui.presentation.viewmodel.NewsViewModel
import vlnny.base.fragment.BaseBindableFragment
import vlnny.base.fragment.BaseFragmentCompanion


class NewsFragment : BaseBindableFragment<FragmentNewsBinding>(), OnArticleItemClick,
    KoinComponent {

    companion object : BaseFragmentCompanion<NewsFragment>() {
        override fun newInstance(): NewsFragment = NewsFragment()
    }

    private val newsViewModel by viewModel<NewsViewModel>()
    //private val keywordPrefs: ISharedPrefsManager by inject()

    private val popup: PopupMenu by lazy {
        PopupMenu(requireContext(), settings).apply {
            inflate(R.menu.item_settings_popup)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.changeKeyword -> {
                        dialogHelper.showAlertDialog()
                    }
                }
                true
            }
        }
    }

    private val dialogHelper: AlertDialogHelper by lazy {
        AlertDialogHelper(requireContext())
    }

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
        newsViewModel.apply {
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
        initSettings()
        initPullToRefresh()
    }

    private fun initSettings() {
        settings.setOnClickListener {
            popup.show()
        }
    }

    private fun initPullToRefresh() {
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorPrimary
            )
        )
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE)

        swipeRefreshLayout.setOnRefreshListener {
            loadNews(newsViewModel.lastQuery)
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
                if (!newsViewModel.isLoadingMore.value!! && totalItemCount <= (lastVisibleItem + 1)) {
                    newsViewModel.isLoadingMore.value = true
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
        query: String = Constants.EMPTY_STRING,
        from: String = Constants.EMPTY_STRING,
        to: String = Constants.EMPTY_STRING,
        language: String = Constants.EMPTY_STRING
    ) =
        newsViewModel.loadEverythingNews(query, from, to, language)

    override fun popupSave(item: ArticleUI) {

    }


}