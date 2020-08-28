package ru.kiryanav.news.data.repository

import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.kiryanav.news.data.mapper.MapperEverythingRequest
import ru.kiryanav.news.data.network.NewsApi
import ru.kiryanav.news.domain.model.NewsUIModel
import ru.kiryanav.news.domain.model.SortBy

class NewsRepository(private val newsApi: NewsApi) : INewsRepository, KoinComponent {

    override suspend fun getEverything(
        query: String,
        from: String,
        to: String,
        language: String,
        dayNumber: Int,
        pageNumber: Int,
        sortBy: SortBy
    ): NewsUIModel {
        val mapper: MapperEverythingRequest by inject()
        val request = mapper.mapToEntity(query, from, to, language, sortBy, dayNumber)

        return mapper.mapFromEntity(
            newsApi.getEverything(
                request.query,
                request.from,
                request.to,
                request.language,
                request.sortBy.keyword,
                pageNumber
            )
        )
    }

}