package ru.kiryanav.data.mapper

import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy
import ru.kiryanav.data.database.entites.ArticleEntity
import ru.kiryanav.data.database.entites.ArticleSourceEntity
import ru.kiryanav.data.dto.news.response.NewsResponse
import ru.kiryanav.data.dto.news.response.SortByForApi
import ru.kiryanav.data.dto.source.Source
import java.time.Instant

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

fun Source.toArticleSource(): ArticleSource =
    ArticleSource(
        id,
        name,
        description,
        url,
        category,
        lang,
        country
    )

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

fun NewsResponse.toNews(): News =
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

fun SortBy.toSortByApi(): SortByForApi =
    when (this) {
        SortBy.POPULARITY -> SortByForApi.POPULARITY
        SortBy.RELEVANCY -> SortByForApi.RELEVANCY
        SortBy.PUBLISHED_AT -> SortByForApi.PUBLISHED_AT
    }

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


