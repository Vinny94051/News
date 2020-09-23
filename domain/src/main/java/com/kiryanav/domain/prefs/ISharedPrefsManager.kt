package com.kiryanav.domain.prefs

interface ISharedPrefsManager {
    fun setInterval(interval : Int)
    fun getInterval(): Int
}