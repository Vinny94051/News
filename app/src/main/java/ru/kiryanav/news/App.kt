package ru.kiryanav.news

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.kiryanav.domain.koin.domainModule
import com.kiryanav.domain.koin.prefsManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.kiryanav.data.koin.dataModule
import ru.kiryanav.ui.R
import ru.kiryanav.ui.koin.viewModelModule
import ru.kiryanav.ui.presentation.worker.NewsWorkManager
import ru.kiryanov.database.koin.databaseModule

class App : Application() {

    companion object {
        const val CHANNEL_ID = "98"
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModule,
                    domainModule,
                    dataModule,
                    databaseModule,
                    prefsManager
                )
            )
        }

        createNotifyChannel()
    }

    private fun createNotifyChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        }
    }
}