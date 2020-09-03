package com.kiryanav.domain.prefs

interface ISharedPrefsManager {
    fun isKeyWordExist(): Boolean
    fun setKeyWord(keyword: String)
    fun getKeyword(): String
}