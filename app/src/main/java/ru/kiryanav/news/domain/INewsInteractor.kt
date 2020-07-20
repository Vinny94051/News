package ru.kiryanav.news.domain

import io.reactivex.Single
import ru.kiryanav.news.domain.model.NewsUIModel

interface INewsInteractor {
    fun getEverything(query: String, from: String, to: String, language: String) : Single<NewsUIModel>
}
