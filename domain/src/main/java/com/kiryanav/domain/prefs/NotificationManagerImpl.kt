package com.kiryanav.domain.prefs

import android.content.Context
import android.content.SharedPreferences
import com.kiryanav.domain.model.TimeInterval

class NotificationManagerImpl(context: Context) : NotificationIntervalManager {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun setInterval(intervalInMnts: TimeInterval?) {
        intervalInMnts?.let { interval ->
            prefs.edit()
                .putLong(INTERVAL_NAME, interval.timeInMnts)
                .apply()
        } ?: prefs.edit()
            .remove(INTERVAL_NAME)
            .apply()
    }

    override fun getInterval() =
        TimeInterval.defineInterval(
            prefs.getLong(INTERVAL_NAME, 0L)
        )

    companion object {
        private const val PREFS_NAME = "ru.kir.prefs.interval.name"
        private const val INTERVAL_NAME = "ru.kir.search.interval"
    }
}