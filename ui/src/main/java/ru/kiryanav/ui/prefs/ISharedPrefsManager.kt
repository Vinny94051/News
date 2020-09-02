package ru.kiryanav.ui.prefs

interface ISharedPrefsManager {

    companion object {
        const val PREFS_NAME = "ru.kir.prefs.keyword.name"
        const val KEYWORD_NAME = "ru.kir.search.keyword"
    }

    fun isKeyWordExist(): Boolean
    fun setKeyWord(keyword : String)
    fun getKeyword(): String
}