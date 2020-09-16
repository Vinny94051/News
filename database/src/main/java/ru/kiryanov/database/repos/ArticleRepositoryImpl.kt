package ru.kiryanov.database.repos

import com.kiryanav.domain.model.Article
import com.kiryanav.domain.repoApi.ArticleRepository
import ru.kiryanov.database.dao.ArticleDao
import ru.kiryanov.database.mapper.toArticle
import ru.kiryanov.database.mapper.toArticleEntity

class ArticleRepositoryImpl(private val articleDao : ArticleDao) : ArticleRepository {
    override suspend fun saveArticle(article: Article) {
        articleDao.insertArticle(
            article.toArticleEntity()
        )
    }

    override suspend fun getAllSavedArticles(): List<Article> {
        return articleDao.getAll().map { entity ->
            entity.toArticle()
        }
    }

    override suspend fun deleteArticle(article: Article) {
        articleDao.deleteArticle(article.toArticleEntity())
    }
}