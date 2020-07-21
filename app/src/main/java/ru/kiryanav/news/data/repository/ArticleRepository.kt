package ru.kiryanav.news.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.kiryanav.news.data.database.dao.ArticleDao
import ru.kiryanav.news.data.mapper.MapperArticle
import ru.kiryanav.news.domain.model.ArticleUI
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val dao: ArticleDao) : IArticleRepository {
    override fun saveArticle(article: ArticleUI): Completable {
        return Completable.fromAction {
            dao.insertCity(
                MapperArticle().mapToEntity(article)
            )
        }
    }

    override fun getAll(): Single<List<ArticleUI>> =
        dao.getAll().map { entities ->
            MapperArticle().mapFromListEntity(entities)
        }
}