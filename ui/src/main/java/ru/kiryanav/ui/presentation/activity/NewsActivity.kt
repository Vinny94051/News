package ru.kiryanav.ui.presentation.activity

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.kiryanav.domain.prefs.ISharedPrefsManager
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.kiryanav.ui.R
import ru.kiryanav.ui.presentation.worker.NewsWorkManager
import vlnny.base.activity.BaseActivity

class NewsActivity : BaseActivity(), KoinComponent {
    override fun layoutId() = R.layout.activity_news
}