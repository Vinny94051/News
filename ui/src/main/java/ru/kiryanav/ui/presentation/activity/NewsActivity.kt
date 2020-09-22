package ru.kiryanav.ui.presentation.activity

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import ru.kiryanav.ui.R
import ru.kiryanav.ui.presentation.worker.NewsWorkManager
import vlnny.base.activity.BaseActivity

class NewsActivity : BaseActivity() {

    init {
            WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                NewsWorkManager.UNIQUE_PERIODIC_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                NewsWorkManager.createPeriodicRequest(15)
            )
    }

    override fun layoutId() = R.layout.activity_news
}