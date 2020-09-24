package ru.kiryanav.ui.presentation.notification

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kiryanav.domain.R
import com.kiryanav.domain.notification.NOTIFICATION_CHANNEL_ID
import vlnny.base.viewModel.BaseViewModel

class NotificationHelper(
    private val context: Context
) : BaseViewModel() {

    private fun createNewNewsNotification(title: String, content: String): Notification {
        return NotificationCompat.Builder(
            context,
            NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_baseline_cloud_done_24)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    fun showNewsNotification(title: String) {
        with(NotificationManagerCompat.from(context)) {
            notify(
                NEWS_NOTIFY_ID, createNewNewsNotification(
                    title,
                    context.getString(R.string.notification_done_message)
                )
            )
        }
    }

    companion object {
        const val NEWS_NOTIFY_ID = 2
    }

}