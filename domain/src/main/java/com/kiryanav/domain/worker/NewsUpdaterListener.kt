package com.kiryanav.domain.worker

import kotlinx.coroutines.flow.Flow


interface NewsUpdaterListener {
    val listener: Flow<Boolean>
}