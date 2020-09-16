package ru.kiryanov.database.mapper

import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource
import ru.kiryanov.database.entites.ArticleEntity
import ru.kiryanov.database.entites.ArticleSourceEntity

fun ArticleEntity.toArticle(): Article =
    Article(
        sourceId,
        sourceName,
        author,
        title,
        description,
        articleUrl,
        previewImageUrl,
        publishedAt,
        content
    )

fun Article.toArticleEntity(): ArticleEntity =
    ArticleEntity(
        sourceId.orEmpty(),
        sourceName.orEmpty(),
        author.orEmpty(),
        title.orEmpty(),
        description.orEmpty(),
        articleUrl.orEmpty(),
        previewImageUrl.orEmpty(),
        publishedAt.orEmpty(),
        content.orEmpty()
    )

fun ArticleSourceEntity.toArticleSource(): ArticleSource =
    ArticleSource(
        id, name, description, url, category, lang, country
    )

fun ArticleSource.toArticleSourceEntity(): ArticleSourceEntity =
    ArticleSourceEntity(
        id ?: throw SourceIdNotFound(),
        lang.orEmpty(),
        country.orEmpty(),
        name.orEmpty(),
        description.orEmpty(),
        category.orEmpty(),
        url.orEmpty()
    )

class SourceIdNotFound : Throwable()