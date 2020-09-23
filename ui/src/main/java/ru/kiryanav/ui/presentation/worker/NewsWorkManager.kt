package ru.kiryanav.ui.presentation.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
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

    private val notifyManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager


    override suspend fun doWork(): Result {
        Log.e(javaClass.simpleName, "Start working")
        setForeground(createForegroundInfo())
        val news = interactor
            .getNews(
                null, null, null, interactor.getSavedSources()
            )
            .articles
            .toArticleItemList(context)

        viewModel.newsLiveData.postValue(news)

        saveCurTime()

        with(NotificationManagerCompat.from(context)) {
            notify(
                NEWS_NOTIFY_ID, createNewsNotify(
                    getPrefs(),
                    (news[1] as ArticleItem.ArticleUI).description
                )
            )
        }

        return Result.success()
    }

    private fun getPrefs(): String {
        return context.getSharedPreferences(P_N, Context.MODE_PRIVATE)
            .getString(TIME, "Nothing") ?: "Error 404"
    }

    private fun saveCurTime() {
        context.getSharedPreferences(P_N, Context.MODE_PRIVATE)
            .edit()
            .putString(TIME, getCurrentTime())
            .apply()
    }

    private fun getCurrentTime() =
        LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))


    private fun createForegroundInfo(): ForegroundInfo {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        return ForegroundInfo(UPDATING_NOTIFY_ID, createUpdatingNotify())
    }

    private fun createUpdatingNotify(): Notification {
        val title = getCurrentTime()
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)

        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText("Start...")
            .setSmallIcon(R.drawable.ic_baseline_arrow_drop_down_24)
            .setOngoing(true)
            .addAction(
                android.R.drawable.ic_delete,
                context.getString(R.string.notify_cancel),
                intent
            )
            .build()
    }

    private fun createChannel() {
        val name = "Channel name"
        val descriptionText = "Description text"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
            .apply {
                description = descriptionText
            }
        notifyManager.createNotificationChannel(channel)
    }


    private fun createNewsNotify(title: String, content: String): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_delete_24)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    companion object {
        const val UNIQUE_PERIODIC_WORK_NAME = "periodic.work.name"
        const val CHANNEL_ID = "78"
        private const val UPDATING_NOTIFY_ID = 1
        private const val NEWS_NOTIFY_ID = 2
        private const val P_N = "90902f"
        private const val TIME = "jojo"

        fun createPeriodicRequest(intervalInMnts: Long) =
            PeriodicWorkRequestBuilder<NewsWorkManager>(
                intervalInMnts,
                TimeUnit.MINUTES
            ).build()
    }
}

