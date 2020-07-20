package ru.kiryanav.news

import android.app.Application
import ru.kiryanav.news.dagger.AppComponent
import ru.kiryanav.news.dagger.AppModule
import ru.kiryanav.news.dagger.DaggerAppComponent

class App : Application() {

    companion object{
        lateinit var appComponent : AppComponent
    }

    init {
        appComponent = initDagger(this)
    }

    private fun initDagger(app : App) : AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
}