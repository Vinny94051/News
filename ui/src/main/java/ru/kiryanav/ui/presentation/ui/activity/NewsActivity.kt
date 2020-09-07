package ru.kiryanav.ui.presentation.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ru.kiryanav.ui.R
import ru.kiryanav.ui.presentation.ui.fragment.NewsFragment
import ru.kiryanav.ui.presentation.ui.navifation.NewsNavigationCallback
import vlnny.base.activity.BaseActivity

class NewsActivity : BaseActivity(), NewsNavigationCallback {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = Navigation
            .findNavController(this, R.id.navHostFragment)
            .apply {
                navigate(R.id.newsFragment)
            }
    }

    override fun layoutId() = R.layout.activity_news

    override fun openSettings() {
        navController.navigate(R.id.action_newsFragment_to_settingsFragment)
    }
}