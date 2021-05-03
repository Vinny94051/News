package com.kiryanav.domain.koin

import com.kiryanav.domain.prefs.NotificationIntervalManager
import com.kiryanav.domain.prefs.NotificationManagerImpl
import com.kiryanav.domain.prefs.TimeManager
import com.kiryanav.domain.prefs.TimeManagerImpl
import org.koin.dsl.module

val prefsManager = module {
    single<NotificationIntervalManager> {
        NotificationManagerImpl(get())
    }

    single<TimeManager> {
        TimeManagerImpl(get())
    }
}