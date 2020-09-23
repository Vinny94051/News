package com.kiryanav.domain.prefs

import android.content.Context
import android.content.SharedPreferences

class PrefsManager(private val context: Context) : ISharedPrefsManager {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun setInterval(interval: Int) {
        prefs.edit()
            .putInt(INTERVAL_NAME, interval)
            .apply()
    }

    override fun getInterval() =
        prefs.getInt(INTERVAL_NAME, MIN_INTERVAL)

    companion object {
        const val PREFS_NAME = "ru.kir.prefs.interval.name"
        const val INTERVAL_NAME = "ru.kir.search.interval"
        private const val MIN_INTERVAL = 15
    }
}