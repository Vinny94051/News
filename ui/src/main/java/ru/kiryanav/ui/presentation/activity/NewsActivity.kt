package ru.kiryanav.ui.presentation.activity


import org.koin.core.KoinComponent
import ru.kiryanav.ui.R
import vlnny.base.activity.BaseActivity


class NewsActivity : BaseActivity(), KoinComponent {
    override fun layoutId() = R.layout.activity_news
}