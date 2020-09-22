package ru.kiryanav.ui.presentation.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.kiryanav.domain.NewsInteractor
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.kiryanav.ui.R
import ru.kiryanav.ui.mapper.toArticleItemList
import ru.kiryanav.ui.model.ArticleItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class NewsWorkManager(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val viewModel = WorkerViewModel
    private val interactor: NewsInteractor by inject()

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager


    override suspend fun doWork(): Result {
        Log.e(javaClass.simpleName, "Start work.")
        setForeground(createForegroundInfo("Start..."))
        val news = interactor
            .getNews(
                null, null, null, interactor.getSavedSources()
            )
            .articles
            .toArticleItemList(context)

        viewModel.newsLiveData.postValue(news)

        createAndShowNotify(
            (news[1] as ArticleItem.ArticleUI).title,
            (news[1] as ArticleItem.ArticleUI).description
        )


        return Result.success()
    }

    private fun getCurrentTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        return current.format(formatter)
    }

    private fun createForegroundInfo(progress: String): ForegroundInfo {
        val id = CHANNEL_ID
        val title = getCurrentTime()
        val cancel = CANCEL

        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(getId())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val notification = NotificationCompat.Builder(applicationContext, id)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(progress)
            .setSmallIcon(R.drawable.ic_baseline_arrow_drop_down_24)
            .setOngoing(true)
            .addAction(android.R.drawable.ic_delete, cancel, intent)
            .build()

        return ForegroundInfo(14,notification)
    }

    private fun createChannel() {
        val name = "Channel name"
        val descriptionText = "Description text"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
            .apply {
                description = descriptionText
            }

        val notifyManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notifyManager.createNotificationChannel(channel)
    }


    private fun createAndShowNotify(title: String, content: String) {
        val notif = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_delete_24)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(14, notif.build())
        }

    }

    companion object {
        const val UNIQUE_PERIODIC_WORK_NAME = "periodic.work.name"
        const val CANCEL = "Cancel"
        const val CHANNEL_ID = "78"
        const val TITLE = "Service"

        fun createPeriodicRequest(intervalInMnts: Long) =
            PeriodicWorkRequestBuilder<NewsWorkManager>(
                intervalInMnts,
                TimeUnit.MINUTES
            ).build()
    }
}

