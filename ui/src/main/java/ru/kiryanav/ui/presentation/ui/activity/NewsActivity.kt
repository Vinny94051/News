package ru.kiryanav.ui.presentation.ui.activity

import android.os.Bundle
import ru.kiryanav.ui.R
import ru.kiryanav.ui.presentation.ui.fragment.NewsFragment
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