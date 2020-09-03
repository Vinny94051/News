package ru.kiryanav.ui.prefs

import android.content.Context
import android.content.SharedPreferences
import ru.kiryanav.ui.Constants

class SharedPrefsManager(private val context: Context) : ISharedPrefsManager {

    companion object {
        const val PREFS_NAME = "ru.kir.prefs.keyword.name"
        const val KEYWORD_NAME = "ru.kir.search.keyword"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun isKeyWordExist(): Boolean =
        getKeyword() != Constants.EMPTY_STRING

    override fun setKeyWord(keyword: String) {
        with(prefs.edit()) {
            putString(KEYWORD_NAME, keyword)
            commit()
        }
    }

    override fun getKeyword(): String =
        prefs.getString(KEYWORD_NAME, Constants.EMPTY_STRING)
            ?: Constants.EMPTY_STRING

}