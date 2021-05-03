package com.kiryanav.domain.worker

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@ExperimentalCoroutinesApi
class NewsUpdater : NewsUpdaterListener, NewsUpdaterReceiver {

    private val channel = BroadcastChannel<Boolean>(1)

    override fun pushNews(isUpdate : Boolean) {
        channel.offer(isUpdate)
    }

    @FlowPreview
    override val listener: Flow<Boolean>
        get() = channel.asFlow()
}