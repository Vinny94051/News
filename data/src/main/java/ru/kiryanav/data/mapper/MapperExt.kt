package ru.kiryanav.data.mapper

import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.News
import ru.kiryanav.data.database.entites.ArticleEntity
import ru.kiryanav.data.dto.news.response.NewsResponse
import java.time.Instant

fun Article.toEntity(): ArticleEntity =
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

fun NewsResponse.toNews() : News =
    News(
        resultsNumber?.toInt(),
        articles.map { article ->
            Article(
                article.source.id,
                article.source.name,
                article.author,
                article.title,
                article.description,
                article.articleUrl,
                article.previewUrl,
                Instant.parse(article.publishedAt).toString(),
                article.content
            )
        }
    )