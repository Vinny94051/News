package ru.kiryanav.data.mapper

import com.kiryanav.domain.model.Article
import ru.kiryanav.data.database.entites.ArticleEntity

fun Article.toEntity(): ArticleEntity =
    ArticleEntity(
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

fun ArticleEntity.toDomainModel(): Article =
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