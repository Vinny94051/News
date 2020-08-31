package ru.kiryanav.news.utils.prefs

interface ISharedPrefsManager {

    companion object {
        const val PREFS_NAME = "ru.kir.prefs.keyword.name"
        const val KEYWORD_NAME = "ru.kir.search.keyword"
        const val EMPTY_STRING = ""
    }

    fun isKeyWordExist(): Boolean
    fun setKeyWord(keyword : String)
    fun getKeyword(): String
}