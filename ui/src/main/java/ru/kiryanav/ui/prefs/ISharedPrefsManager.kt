package ru.kiryanav.ui.prefs

interface ISharedPrefsManager {
    fun isKeyWordExist(): Boolean
    fun setKeyWord(keyword: String)
    fun getKeyword(): String
}