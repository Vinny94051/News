package ru.kiryanav.news.presentation.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import ru.kiryanav.news.Constants
import ru.kiryanav.news.R
import ru.kiryanav.news.databinding.FragmentNewsBinding
import ru.kiryanav.news.domain.model.ArticleUI
import ru.kiryanav.news.presentation.ui.list.OnArticleItemClick
import ru.kiryanav.news.presentation.ui.view.AlertDialogHelper
import ru.kiryanav.news.presentation.viewmodel.NewsViewModel
import ru.kiryanav.news.utils.prefs.ISharedPrefsManager
import vlnny.base.fragment.BaseBindableFragment
import vlnny.base.fragment.BaseFragmentCompanion


class NewsFragment : BaseBindableFragment<FragmentNewsBinding>(), OnArticleItemClick,
    KoinComponent {

    companion object : BaseFragmentCompanion<NewsFragment>() {
        override fun newInstance(): NewsFragment = NewsFragment()
    }

    private lateinit var viewModel: NewsViewModel

    private val prefs: ISharedPrefsManager by inject()

    private val popup : PopupMenu by lazy {
        PopupMenu(context, settings).apply {
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
        AlertDialogHelper(context!!)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            this.viewModel = this@NewsFragment.viewModel
            this.callback = this@NewsFragment
            executePendingBindings()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogHelper.closeLiveData.observe(this, Observer {
            loadNews(prefs.getKeyword())
        })
        if (!prefs.isKeyWordExist()) {
            dialogHelper.showAlertDialog()
        } else {
            dialogHelper.closeLiveData.value = true
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
        initSettings()
        initPullToRefresh()
    }

    private fun initSettings() {
        settings.setOnClickListener{
            popup.show()
        }
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

            override fun onQueryTextChange(newText: String?) : Boolean {
               if (newText.isNullOrEmpty()){
                   loadNews(prefs.getKeyword())
                   searchView.isIconified = true
               }
                return true
            }
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