package ru.kiryanav.data.repository

import com.kiryanav.domain.Error
import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.repoApi.NewsRepository
import ru.kiryanav.data.mapper.toArticleSource
import ru.kiryanav.data.mapper.toNews
import ru.kiryanav.data.mapper.toSortByApi
import ru.kiryanav.data.network.NewsApi
import vlnny.base.data.model.ResponseResult
import vlnny.base.data.repository.BaseRepository
import java.util.*

class NewsRepositoryImpl(private val newsApi: NewsApi) : BaseRepository(), NewsRepository {

    override suspend fun getNews(
        query: String?,
        from: String?,
        to: String?,
        sources: List<ArticleSource>,
        language: String?,
        pageNumber: Int,
        sortBy: SortBy
    ): ResponseResult<News, Error> =
        withErrorHandlingCall {
            val sourcesIds = createSourcesParam(sources)
            newsApi.getEverything(query, from, to, getLanguage(language),
                sortBy.toSortByApi().keyword,
                pageNumber,
                if (sourcesIds.isNotEmpty()) sourcesIds else DEFAULT_SOURCE
            ).toNews()
        }

    override suspend fun getSources(language: String)
            : ResponseResult<List<ArticleSource>, Error> =
        withErrorHandlingCall {
            newsApi.getSourcesByLanguage(
                language
            ).sources.map { source ->
                source.toArticleSource()
            }
        }

    private fun createSourcesParam(sources: List<ArticleSource>): String {
        var sourcesId = ""
        for (element in sources) {
            sourcesId = sourcesId.plus(element.id).plus(",")
        }
        return sourcesId
    }

    private fun getLanguage(language: String?): String =
        language ?: Locale.getDefault().language

    companion object {
        const val DEFAULT_SOURCE = "RBC"
    }

}