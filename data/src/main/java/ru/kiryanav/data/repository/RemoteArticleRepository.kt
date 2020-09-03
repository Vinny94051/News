package ru.kiryanav.data.repository

import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy
import com.kiryanav.domain.repoApi.RemoteRepository
import ru.kiryanav.data.mapper.MapperNewsRequest
import ru.kiryanav.data.mapper.toNews
import ru.kiryanav.data.network.NewsApi

class RemoteArticleRepository(
    private val newsApi: NewsApi,
    private val mapper: MapperNewsRequest
) :
    RemoteRepository {

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

        return newsApi.getEverything(
            request.query,
            request.from,
            request.to,
            request.language,
            request.sortBy.keyword,
            pageNumber
        ).toNews()
    }


}