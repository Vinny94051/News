package ru.kiryanov.database.repos

import com.kiryanav.domain.Error
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.repoApi.SourceRepository
import ru.kiryanov.database.dao.ArticleSourceDao
import ru.kiryanov.database.mapper.toArticleSource
import ru.kiryanov.database.mapper.toArticleSourceEntity
import vlnny.base.data.model.ResponseResult
import vlnny.base.data.repository.BaseRepository

class SourceRepositoryImpl(private val sourcesDao: ArticleSourceDao) : BaseRepository(),
    SourceRepository {

    override suspend fun getSavedSources(): ResponseResult<List<ArticleSource>, Error> =
        withErrorHandlingCall {
            sourcesDao.getAll().map { entity ->
                entity.toArticleSource()
            }
        }

    override suspend fun insertSources(source: List<ArticleSource>): ResponseResult<Unit, Error> =
        withErrorHandlingCall {
            for (element in source) {
                sourcesDao.insertSources(element.toArticleSourceEntity())
            }
        }

    override suspend fun deleteSource(source: ArticleSource): ResponseResult<Unit, Error> =
        withErrorHandlingCall {
            sourcesDao.deleteSource(source.toArticleSourceEntity())
        }
}