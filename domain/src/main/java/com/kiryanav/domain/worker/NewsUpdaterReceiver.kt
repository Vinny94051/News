package com.kiryanav.domain.worker

import kotlinx.coroutines.flow.Flow

interface NewsUpdaterReceiver {
    fun pushNews(isUpdate : Boolean)
}