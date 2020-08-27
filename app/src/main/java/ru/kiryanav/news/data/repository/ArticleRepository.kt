package ru.kiryanav.news.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.kiryanav.news.data.database.dao.ArticleDao
import ru.kiryanav.news.data.mapper.MapperArticle
import ru.kiryanav.news.domain.model.ArticleUI

class ArticleRepository(private val articleDao: ArticleDao) : IArticleRepository {

    override fun saveArticle(article: ArticleUI): Completable {
        return Completable.fromAction {
            articleDao.insertCity(
                MapperArticle().mapToEntity(article)
            )
        }
    }

    override fun getAll(): Single<List<ArticleUI>> =
        articleDao.getAll().map { entities ->
            MapperArticle().mapFromListEntity(entities)
        }
}