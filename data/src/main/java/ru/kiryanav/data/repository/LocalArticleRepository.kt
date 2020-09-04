package ru.kiryanav.data.repository

import ru.kiryanav.data.database.dao.ArticleDao
import com.kiryanav.domain.model.Article
import com.kiryanav.domain.repoApi.LocalNewsRepository
import ru.kiryanav.data.mapper.toArticle
import ru.kiryanav.data.mapper.toArticleEntity

class LocalArticleRepository(private val articleDao: ArticleDao) : LocalNewsRepository {

    override suspend fun saveArticle(article: Article) {
        articleDao.insertCity(
            article.toArticleEntity()
        )
    }

    override suspend fun getAll(): List<Article> =
        articleDao.getAll().map { entity ->
            entity.toArticle()
        }
}