package com.kiryanav.domain.prefs

interface SourceManager {
    fun saveSource(source : String)
    fun getSource() : String
}