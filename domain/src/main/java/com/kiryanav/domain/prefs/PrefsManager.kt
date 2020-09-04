package com.kiryanav.domain.prefs

import android.content.Context
import android.content.SharedPreferences
import com.kiryanav.domain.Constants

class PrefsManager(context: Context) : IKeywordManager, SourceManager {

    private val keywordPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME_KEYWORD, Context.MODE_PRIVATE)
    }

    private val sourcePrefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME_SOURCE, Context.MODE_PRIVATE)
    }

    override fun isKeyWordExist(): Boolean =
        getKeyword() != Constants.EMPTY_STRING

    override fun setKeyWord(keyword: String) {
        with(keywordPrefs.edit()) {
            putString(KEYWORD_NAME, keyword)
            commit()
        }
    }

    override fun getKeyword(): String =
        keywordPrefs.getString(KEYWORD_NAME, Constants.EMPTY_STRING)
            ?: Constants.EMPTY_STRING


    override fun saveSource(source: String) {
        with(sourcePrefs.edit()) {
            putString(SOURCE_NAME, source)
            commit()
        }
    }

    override fun getSource(): String =
        sourcePrefs.getString(SOURCE_NAME, Constants.EMPTY_STRING)
            ?: Constants.EMPTY_STRING

    companion object {
        const val PREFS_NAME_KEYWORD = "ru.kir.prefs.keyword.name"
        const val PREFS_NAME_SOURCE = "ru.kir.prefs.source.name"
        const val KEYWORD_NAME = "ru.kir.search.keyword"
        const val SOURCE_NAME = "ru.kir.settings.source"
    }
}