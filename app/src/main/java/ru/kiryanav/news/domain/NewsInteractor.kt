package ru.kiryanav.news.domain

import io.reactivex.schedulers.Schedulers
import ru.kiryanav.news.data.repository.INewsRepository
import javax.inject.Inject

class NewsInteractor @Inject constructor(private val newsRepo: INewsRepository) : INewsInteractor {

   override fun getEverything(query: String, from: String, to: String, language: String) =
        newsRepo.getEverything(query, from, to, language)
            .subscribeOn(Schedulers.io())

}