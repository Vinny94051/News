package com.kiryanav.domain.prefs

interface TimeManager {
    fun saveCurrentTime()
    fun getLastSavedTime() : String
    fun getCurrentTime(format : String = "HH:mm") : String
}