package ru.kiryanav.news.utils.prefs

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsManager(private val context: Context) : ISharedPrefsManager {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(ISharedPrefsManager.PREFS_NAME, Context.MODE_PRIVATE)

    override fun isKeyWordExist(): Boolean =
        getKeyword() != ISharedPrefsManager.EMPTY_STRING

    override fun setKeyWord(keyword: String) {
        with(prefs.edit()) {
            putString(ISharedPrefsManager.KEYWORD_NAME, keyword)
            commit()
        }
    }

    override fun getKeyword(): String =
        prefs.getString(ISharedPrefsManager.KEYWORD_NAME, ISharedPrefsManager.EMPTY_STRING)
            ?: ISharedPrefsManager.EMPTY_STRING

}