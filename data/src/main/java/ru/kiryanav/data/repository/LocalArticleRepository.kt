package ru.kiryanav.data.repository

import ru.kiryanav.data.database.dao.ArticleDao
import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.repoApi.LocalNewsRepository
import ru.kiryanav.data.database.dao.ArticleSourceDao
import ru.kiryanav.data.mapper.toArticle
import ru.kiryanav.data.mapper.toArticleEntity
import ru.kiryanav.data.mapper.toArticleSource
import ru.kiryanav.data.mapper.toArticleSourceEntity

class LocalArticleRepository(
private val articleDao: ArticleDao,
private val sourcesDao: ArticleSourceDao
) : LocalNewsRepository {

    override suspend fun saveArticle(article: Article) {
        articleDao.insertArticle(
            article.toArticleEntity()
        )
    }

    override suspend fun getAllSavedArticles(isLocalSavedFlagNeedToBeTrue: Boolean): List<Article> =
        articleDao.getAll().map { entity ->
            entity.toArticle().apply {
                if (isLocalSavedFlagNeedToBeTrue) {
                    isLocalSaved = true
                }
            }
        }

    override suspend fun getAllSources(): List<ArticleSource> =
        sourcesDao.getAll().map { entity ->
            entity.toArticleSource()
        }

    override suspend fun insertSources(source: List<ArticleSource>) {
        for (element in source) {
            sourcesDao.insertSources(element.toArticleSourceEntity())
        }
    }

    override suspend fun deleteSource(source: ArticleSource) {
        sourcesDao.deleteSource(source.toArticleSourceEntity())
    }

    override suspend fun deleteArticle(article: Article) {
        articleDao.deleteArticle(article.toArticleEntity())
    }
}