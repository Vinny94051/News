package ru.kiryanav.news

import android.app.Application
import com.kiryanav.domain.koin.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.kiryanav.data.koin.dataModule
import ru.kiryanav.ui.koin.viewModelModule
import ru.kiryanov.database.koin.databaseModule

class App : Application() {

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
                    databaseModule
                )
            )
        }
    }
}