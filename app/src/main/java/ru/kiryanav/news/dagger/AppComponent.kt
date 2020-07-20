package ru.kiryanav.news.dagger

import dagger.Component
import ru.kiryanav.news.data.mapper.MapperEverythingRequest
import ru.kiryanav.news.presentation.viewmodel.NewsViewModel
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mapperEverythingRequest: MapperEverythingRequest)
    fun inject(mapperEverythingRequest: NewsViewModel)
}