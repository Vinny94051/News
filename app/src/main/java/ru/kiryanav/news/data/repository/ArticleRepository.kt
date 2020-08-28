package ru.kiryanav.news.data.repository

import ru.kiryanav.news.data.database.dao.ArticleDao
import ru.kiryanav.news.data.mapper.MapperArticle
import ru.kiryanav.news.domain.model.ArticleUI

class ArticleRepository(private val articleDao: ArticleDao) : IArticleRepository {

    override suspend fun saveArticle(article: ArticleUI) {
            articleDao.insertCity(
                MapperArticle().mapToEntity(article)
            )
    }

    override suspend fun getAll(): List<ArticleUI> =
            MapperArticle().mapFromListEntity(articleDao.getAll())
}