package ru.kiryanav.data.mapper

import android.annotation.SuppressLint
import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy
import ru.kiryanav.data.dto.news.response.NewsResponse
import ru.kiryanav.data.dto.news.response.SortByForApi
import ru.kiryanav.data.dto.source.Source
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

internal const val ISO_8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

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
                article.publishedAt.fromEpochToDate(),
                article.content
            )
        }
    )


@SuppressLint("SimpleDateFormat")
internal fun String?.fromEpochToDate(): Date? {
    return SimpleDateFormat(ISO_8601_DATE_FORMAT).parse(this ?: return null)
}

@SuppressLint("SimpleDateFormat")
internal fun Date?.toEpoch(): String {
    return SimpleDateFormat(ISO_8601_DATE_FORMAT).format(this ?: Date())
}

fun SortBy.toSortByApi(): SortByForApi =
    when (this) {
        SortBy.POPULARITY -> SortByForApi.POPULARITY
        SortBy.RELEVANCY -> SortByForApi.RELEVANCY
        SortBy.PUBLISHED_AT -> SortByForApi.PUBLISHED_AT
    }
