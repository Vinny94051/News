package ru.kiryanav.news.data.repository

import io.reactivex.Single
import ru.kiryanav.news.data.mapper.MapperEverythingRequest
import ru.kiryanav.news.data.network.NewsApi
import ru.kiryanav.news.domain.model.NewsUIModel
import ru.kiryanav.news.domain.model.SortBy
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) : INewsRepository {

    override fun getEverything(
        query: String,
        from: String,
        to: String,
        language: String,
        dayNumber : Int,
        pageNumber: Int,
        sortBy: SortBy
    ): Single<NewsUIModel> {
        val mapper = MapperEverythingRequest()
        val request = mapper.mapToEntity(query, from, to, language, sortBy, dayNumber)

        return newsApi.getEverything(
            request.query,
            request.from,
            request.to,
            request.language,
            request.sortBy.keyword,
            pageNumber
        ).map { response ->
            mapper.mapFromEntity(response)
        }

    }

}