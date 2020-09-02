package ru.kiryanav.data.repository

import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy
import com.kiryanav.domain.repoApi.INewsRepository
import ru.kiryanav.data.mapper.MapperNewsRequest
import ru.kiryanav.data.network.NewsApi

class NewsRepository(
    private val newsApi: NewsApi,
    private val mapper: MapperNewsRequest
) :
    INewsRepository {

    override suspend fun getEverything(
        query: String,
        from: String,
        to: String,
        language: String,
        dayNumber: Int,
        pageNumber: Int,
        sortBy: SortBy
    ): News {
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