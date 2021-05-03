package ru.kiryanov.database.repos

import com.kiryanav.domain.error.NewsError
import com.kiryanav.domain.model.Article
import com.kiryanav.domain.repoApi.ArticleRepository
import ru.kiryanov.database.dao.ArticleDao
import ru.kiryanov.database.mapper.toArticle
import ru.kiryanov.database.mapper.toArticleEntity
import vlnny.base.data.model.ResponseResult
import vlnny.base.data.repository.BaseRepository

class ArticleRepositoryImpl(private val articleDao: ArticleDao) : BaseRepository(),
    ArticleRepository {

    override suspend fun saveArticle(article: Article): ResponseResult<Unit, NewsError> =
        withErrorHandlingCall({
            articleDao.insertArticle(
                article.toArticleEntity()
            )
        }, {
            NewsError.Unknown
        })


    override suspend fun getAllSavedArticles(): ResponseResult<List<Article>, NewsError> {
        return withErrorHandlingCall({
            articleDao.getAll().map { entity ->
                entity.toArticle()
            }
        }, {
            NewsError.Unknown
        })
    }

    override suspend fun deleteArticle(article: Article): ResponseResult<Unit, NewsError> =
        withErrorHandlingCall({
            articleDao.deleteArticle(article.toArticleEntity())
        }, {
            NewsError.Unknown
        })
}
