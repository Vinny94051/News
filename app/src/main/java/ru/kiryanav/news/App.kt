package ru.kiryanav.news

import android.app.Application
import com.kiryanav.domain.koin.domainModule
import com.kiryanav.domain.koin.prefsManager
import com.kiryanav.domain.prefs.TimeManager
import com.kiryanav.domain.worker.NewsUpdaterListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.kiryanav.data.koin.dataModule
import ru.kiryanav.ui.koin.viewModelModule
import ru.kiryanav.ui.presentation.notification.NotificationHelper
import ru.kiryanov.database.koin.databaseModule

class App : Application() {

    private val notificationListener: NewsUpdaterListener by inject()
    private val notificationHelper: NotificationHelper by inject()
    private val timeManager: TimeManager by inject()
    private val job = CoroutineScope(Job())

    @Suppress("EXPERIMENTAL_API_USAGE")
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModule,
                    domainModule,
                    dataModule,
                    databaseModule,
                    prefsManager
                )
            )
        }
        setNewsUpdateNotificationListener()
    }

    private fun setNewsUpdateNotificationListener() {
        job.launch {
            notificationListener.listener.collect { isUpdate ->
                if (isUpdate) {
                    notificationHelper.showNewsNotification(timeManager.getLastSavedTime())
                }
            }
        }
    }
}