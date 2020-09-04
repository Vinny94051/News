package ru.kiryanav.data.repository

import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.prefs.SourceManager
import com.kiryanav.domain.repoApi.RemoteNewsRepository
import ru.kiryanav.data.mapper.toArticleSource
import ru.kiryanav.data.mapper.toNews
import ru.kiryanav.data.mapper.toSortByApi
import ru.kiryanav.data.network.NewsApi
import java.util.*

class RemoteArticleRepository(
    private val newsApi: NewsApi,
    private val prefsManager: SourceManager
) : RemoteNewsRepository {

    override suspend fun getEverything(
        query: String,
        from: String,
        to: String,
        language: String,
        pageNumber: Int,
        sortBy: SortBy
    ): News =
        newsApi.getEverything(
            query,
            from,
            to,
            getLanguage(language),
            sortBy.toSortByApi().keyword,
            pageNumber,
            prefsManager.getSource()
        ).toNews()

    override suspend fun getSourcesByLanguage(language: String): List<ArticleSource> =
        newsApi.getSourcesByLanguage(
            language
        ).sources.map { source ->
            source.toArticleSource()
        }


    private fun getLanguage(language: String): String =
        if (language.isEmpty()) Locale.getDefault().language else language

}