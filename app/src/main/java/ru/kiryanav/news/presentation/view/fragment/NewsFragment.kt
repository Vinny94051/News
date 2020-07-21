package ru.kiryanav.news.presentation.view.fragment

import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
            loadNews("Лукашенко")
        }
    }

    override fun layoutId() = R.layout.fragment_news

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
            .apply {
                isArticleSavedLiveData.observe(this@NewsFragment, Observer { isSaved ->
                    if (isSaved) {
                        showSnack("Article successfully saved")
                    } else showSnack("Something went wrong")
                })
            }
    }

    override fun initViews() {
        super.initViews()
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

    override fun popupSave(item: ArticleUI) {
        viewModel.saveArticle(item)
    }


    private fun loadNews(
        query: String,
        from: String = Constants.EMPTY_STRING,
        to: String = Constants.EMPTY_STRING,
        language: String = Constants.EMPTY_STRING
    ) =
        viewModel.loadEverythingNews(query, from, to, language)


}