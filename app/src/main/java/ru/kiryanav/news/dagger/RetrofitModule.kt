package ru.kiryanav.news.dagger

import dagger.Module
import dagger.Provides
import ru.kiryanav.news.data.network.NewsApi
import ru.kiryanav.news.data.network.RetrofitClient


@Module
class RetrofitModule {
    @Provides
    fun provideNewsApi() : NewsApi = RetrofitClient.getNewsApi()
}