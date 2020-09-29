package ru.kiryanov.database.repos

import com.kiryanav.domain.Error
import com.kiryanav.domain.model.Article
import com.kiryanav.domain.repoApi.ArticleRepository
import ru.kiryanov.database.dao.ArticleDao
import ru.kiryanov.database.mapper.toArticle
import ru.kiryanov.database.mapper.toArticleEntity
import vlnny.base.data.model.ResponseResult
import vlnny.base.data.repository.BaseRepository

class ArticleRepositoryImpl(private val articleDao: ArticleDao) : BaseRepository(),
    ArticleRepository {
    override suspend fun saveArticle(article: Article): ResponseResult<Unit, Error> =
        withErrorHandlingCall {
            articleDao.insertArticle(
                article.toArticleEntity()
            )
        }


    override suspend fun getAllSavedArticles(): ResponseResult<List<Article>, Error> {
        return withErrorHandlingCall {
            articleDao.getAll().map { entity ->
                entity.toArticle()
            }
        }
    }

    override suspend fun deleteArticle(article: Article): ResponseResult<Unit, Error> =
        withErrorHandlingCall {
            articleDao.deleteArticle(article.toArticleEntity())
        }
}
