package com.kiryanav.domain.prefs

import com.kiryanav.domain.model.TimeInterval

interface NotificationIntervalManager {
    fun setInterval(intervalInMnts : TimeInterval?)
    fun getInterval(): TimeInterval?
}