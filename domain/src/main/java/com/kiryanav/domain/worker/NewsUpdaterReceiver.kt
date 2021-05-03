package com.kiryanav.domain.worker

interface NewsUpdaterReceiver {
    fun pushNews(isUpdate : Boolean)
}