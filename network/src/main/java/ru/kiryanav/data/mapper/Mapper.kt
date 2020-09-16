package ru.kiryanav.data.mapper

import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy
import ru.kiryanav.data.dto.news.response.NewsResponse
import ru.kiryanav.data.dto.news.response.SortByForApi
import ru.kiryanav.data.dto.source.Source
import java.time.Instant


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
