package ru.kiryanav.data.repository

import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.repoApi.RemoteNewsRepository
import ru.kiryanav.data.mapper.toArticleSource
import ru.kiryanav.data.mapper.toNews
import ru.kiryanav.data.mapper.toSortByApi
import ru.kiryanav.data.network.NewsApi
import java.util.*

class RemoteArticleRepository(
    private val newsApi: NewsApi
) : RemoteNewsRepository {

    override suspend fun getNews(
        query: String,
        from: String,
        to: String,
        sources: List<ArticleSource>,
        language: String,
        pageNumber: Int,
        sortBy: SortBy
    ): News {
        var articleSources = ""
        for (element in sources) {
            articleSources = articleSources.plus(element.name).plus(",")
        }
        return newsApi.getEverything(
            query,
            from,
            to,
            getLanguage(language),
            sortBy.toSortByApi().keyword,
            pageNumber,
            if (articleSources.isNotEmpty()) articleSources else "RBC"
        ).toNews()
    }

    override suspend fun getSourcesByLanguage(language: String): List<ArticleSource> =
        newsApi.getSourcesByLanguage(
            language
        ).sources.map { source ->
            source.toArticleSource()
        }


    private fun getLanguage(language: String): String =
        if (language.isEmpty()) Locale.getDefault().language else language

}