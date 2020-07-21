package ru.kiryanav.news.presentation.view.activity

import android.os.Bundle
import ru.kiryanav.news.R
import ru.kiryanav.news.presentation.view.fragment.NewsFragment
import vlnny.base.activity.BaseActivity
import vlnny.base.ext.hideActionBar

class NewsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router.addFragmentWithAddingInBackStack(
            NewsFragment.newInstance(),
            R.id.mainContainer,
            NewsFragment.id
        )
    }

    override fun layoutId() = R.layout.activity_news
}