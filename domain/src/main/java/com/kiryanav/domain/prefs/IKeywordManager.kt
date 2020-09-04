package com.kiryanav.domain.prefs

interface IKeywordManager {
    fun isKeyWordExist(): Boolean
    fun setKeyWord(keyword: String)
    fun getKeyword(): String
}