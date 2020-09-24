package com.kiryanav.domain.notification

import android.app.NotificationChannel
import android.app.NotificationManager

const val NOTIFICATION_CHANNEL_ID = "ru.kiryanov.notify.channel.id.01"
private const val NOTIFICATION_CHANNEL_NAME = "ru.kiryanov.notify.channel.name.01"

internal fun createNotificationChannel(
    notificationManager: NotificationManager,
    descriptionText: String
) {
    notificationManager.createNotificationChannel(NotificationChannel(
        NOTIFICATION_CHANNEL_ID,
        NOTIFICATION_CHANNEL_NAME,
        NotificationManager.IMPORTANCE_DEFAULT
    )
        .apply {
            description = descriptionText
        })
}