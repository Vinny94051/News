package ru.kiryanav.news

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.kiryanav.news.koin.dataModule
import ru.kiryanav.news.koin.domainModule
import ru.kiryanav.news.koin.mapperModule
import ru.kiryanav.news.koin.reposModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(dataModule, domainModule, reposModule, mapperModule))
        }
    }
}