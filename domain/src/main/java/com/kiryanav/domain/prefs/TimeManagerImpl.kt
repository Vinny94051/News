package com.kiryanav.domain.prefs

import android.content.Context
import com.kiryanav.domain.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimeManagerImpl(private val context: Context) : TimeManager {

    private val prefs =
        context.getSharedPreferences(TIME_PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveCurrentTime() {
        prefs.edit()
            .putString(TIME_STRING_NAME, getCurrentTime())
            .apply()
    }

    override fun getLastSavedTime(): String {
        return prefs.getString(
            TIME_STRING_NAME,
            context.getString(R.string.error_time_not_found_default_value)
        ) ?: context.getString(R.string.error_time_not_found_default_value)
    }

    override fun getCurrentTime(format: String): String =
        LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern(format))


    companion object {
        private const val TIME_PREFS_NAME = "ru.kiryanov.domain.prefs.time"
        private const val TIME_STRING_NAME = "ru.kiryanov.domain.name.time"
    }
}