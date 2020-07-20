package ru.kiryanav.news.dagger

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(
    includes = [
        RetrofitModule::class,
        RepositoryModule::class
    ]
)
class AppModule(private val app: Application) {
    @Singleton
    @Provides
    fun provideContext(): Context = app
}