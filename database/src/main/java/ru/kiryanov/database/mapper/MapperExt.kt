package ru.kiryanov.database.mapper

import android.annotation.SuppressLint
import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource
import ru.kiryanov.database.entites.ArticleEntity
import ru.kiryanov.database.entites.ArticleSourceEntity
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

fun ArticleEntity.toArticle(): Article =
    Article(
        sourceId,
        sourceName,
        author,
        title,
        description,
        articleUrl,
        previewImageUrl,
        epochToDate(publishedAt),
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
        dateToString(publishedAt),
        content.orEmpty()
    )

@SuppressLint("SimpleDateFormat")
private fun dateToString(date: Date?): String {
    val sdf = SimpleDateFormat(DATE_FORMAT)
    return sdf.format(date ?: return "null")
}

//2020-09-10T14:28:00Z
@SuppressLint("SimpleDateFormat")
private fun epochToDate(epochDate: String?): Date? {
    val dateFormat = SimpleDateFormat(DATE_FORMAT)
    return dateFormat.parse(epochDate ?: return null)
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