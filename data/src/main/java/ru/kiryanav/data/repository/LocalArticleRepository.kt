package ru.kiryanav.data.repository

import ru.kiryanav.data.database.dao.ArticleDao
import com.kiryanav.domain.model.Article
import com.kiryanav.domain.repoApi.LocalRepository
import ru.kiryanav.data.mapper.toDomainModel
import ru.kiryanav.data.mapper.toEntity

class LocalArticleRepository(private val articleDao: ArticleDao) : LocalRepository {

    override suspend fun saveArticle(article: Article) {
        articleDao.insertCity(
            article.toEntity()
        )
    }

    override suspend fun getAll(): List<Article> =
        articleDao.getAll().map { entity ->
            entity.toDomainModel()
        }
}