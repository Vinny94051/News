package ru.kiryanov.database.repos

import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.repoApi.SourceRepository
import ru.kiryanov.database.dao.ArticleSourceDao
import ru.kiryanov.database.mapper.toArticleSource
import ru.kiryanov.database.mapper.toArticleSourceEntity

class SourceRepositoryImpl(private val sourcesDao: ArticleSourceDao) : SourceRepository {
    override suspend fun getSavedSources(): List<ArticleSource> {
        return sourcesDao.getAll().map { entity ->
            entity.toArticleSource()
        }
    }

    override suspend fun insertSources(source: List<ArticleSource>) {
        for (element in source) {
            sourcesDao.insertSources(element.toArticleSourceEntity())
        }
    }

    override suspend fun deleteSource(source: ArticleSource) {
        sourcesDao.deleteSource(source.toArticleSourceEntity())
    }
}