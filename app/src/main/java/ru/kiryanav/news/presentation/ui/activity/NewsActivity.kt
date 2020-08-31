package ru.kiryanav.news.presentation.ui.activity

import android.os.Bundle
import ru.kiryanav.news.R
import ru.kiryanav.news.presentation.ui.fragment.NewsFragment
import vlnny.base.activity.BaseActivity

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