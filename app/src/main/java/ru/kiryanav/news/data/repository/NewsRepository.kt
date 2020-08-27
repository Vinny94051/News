package ru.kiryanav.news.data.repository

import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.kiryanav.news.data.mapper.MapperEverythingRequest
import ru.kiryanav.news.data.network.NewsApi
import ru.kiryanav.news.domain.model.NewsUIModel
import ru.kiryanav.news.domain.model.SortBy

class NewsRepository(private val newsApi: NewsApi) : INewsRepository, KoinComponent {

    override fun getEverything(
        query: String,
        from: String,
        to: String,
        language: String,
        dayNumber: Int,
        pageNumber: Int,
        sortBy: SortBy
    ): Single<NewsUIModel> {
        val mapper: MapperEverythingRequest by inject()
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