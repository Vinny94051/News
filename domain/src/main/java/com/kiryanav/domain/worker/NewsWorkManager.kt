package com.kiryanav.domain.worker

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.kiryanav.domain.NewsInteractor
import com.kiryanav.domain.R
import com.kiryanav.domain.model.TimeInterval
import com.kiryanav.domain.notification.NOTIFICATION_CHANNEL_ID
import com.kiryanav.domain.notification.createNotificationChannel
import com.kiryanav.domain.prefs.TimeManager
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.TimeUnit

class NewsWorkManager(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val interactor: NewsInteractor by inject()
    private val timeManager: TimeManager by inject()
    private val newsUpdateReceiver: NewsUpdaterReceiver by inject()

    private val notifyManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager


    override suspend fun doWork(): Result {
        Log.e(javaClass.simpleName, "Start working")
        setForeground(createForegroundInfo())
        timeManager.saveCurrentTime()
        val oldTitle: String?
        val newTitle: String?

        interactor.apply {
            oldTitle = getSavedArticles()[0].item.title
            newTitle = getNews(
                null,
                null,
                null,
                getSavedSources()
            ).articles[0].item.title
        }

        newsUpdateReceiver.pushNews(oldTitle == newTitle)
        return Result.success()
    }

    private fun createForegroundInfo(): ForegroundInfo {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notifyManager, "News channel")
        }
        return ForegroundInfo(UPDATING_NOTIFY_ID, createUpdatingNotify())
    }

    private fun createUpdatingNotify(): Notification {
        val title = timeManager.getCurrentTime()
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)

        return NotificationCompat.Builder(
            applicationContext,
            NOTIFICATION_CHANNEL_ID
        )
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(context.getString(R.string.title_foreground_notify))
            .setSmallIcon(R.drawable.ic_baseline_cahced_24)
            .setOngoing(true)
            .addAction(
                android.R.drawable.ic_delete,
                context.getString(R.string.notify_cancel),
                intent
            )
            .build()
    }

    companion object {
        const val UNIQUE_PERIODIC_WORK_NAME = "periodic.work.name"
        private const val UPDATING_NOTIFY_ID = 1

        fun createPeriodicRequest(timeInterval: TimeInterval) =
            PeriodicWorkRequestBuilder<NewsWorkManager>(
                timeInterval.timeInMnts,
                TimeUnit.MINUTES
            ).build()
    }
}

