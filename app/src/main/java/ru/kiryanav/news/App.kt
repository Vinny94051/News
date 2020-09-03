package ru.kiryanav.news

import android.app.Application
import android.content.Intent
import com.kiryanav.domain.koin.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.kiryanav.data.koin.dataModule
import ru.kiryanav.data.koin.mapperModule
import ru.kiryanav.data.koin.repoModule
import ru.kiryanav.ui.koin.prefsModule
import ru.kiryanav.ui.koin.viewModelModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(prefsModule, viewModelModule, dataModule, domainModule, repoModule, mapperModule))
        }
    }
}