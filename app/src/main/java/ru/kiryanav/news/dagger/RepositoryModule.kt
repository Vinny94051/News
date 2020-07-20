package ru.kiryanav.news.dagger

import dagger.Binds
import dagger.Module
import ru.kiryanav.news.data.repository.INewsRepository
import ru.kiryanav.news.data.repository.NewsRepository
import ru.kiryanav.news.domain.INewsInteractor
import ru.kiryanav.news.domain.NewsInteractor

@Module
interface RepositoryModule {
    @Binds
    fun bindNewsRepo(newsRepo : NewsRepository) : INewsRepository

    @Binds
    fun bindNewsInteractor(newsInteractor : NewsInteractor) : INewsInteractor
}