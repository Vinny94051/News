package ru.kiryanav.ui.mapper

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource
import ru.kiryanav.ui.R
import ru.kiryanav.ui.model.ArticleSourceUI
import ru.kiryanav.ui.model.ArticleUI
import java.text.SimpleDateFormat

fun ArticleUI.toArticle(): Article =
    item

private const val DATE_INPUT = "yyyy-MM-dd"
private const val DATE_OUTPUT = "dd.MM.yyyy"

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("SimpleDateFormat")
fun Article.toArticleUI(context: Context): ArticleUI =
    ArticleUI(
        if (!author.isNullOrEmpty()) {
            context.getString(R.string.author).format(
                author
            )
        } else {
            context.getString(R.string.unknown_author)
        },
        title.orEmpty(),
        description.orEmpty(),
        articleUrl.orEmpty(),
        previewImageUrl.orEmpty(),
        if (!publishedAt.isNullOrEmpty()) {
            SimpleDateFormat(DATE_OUTPUT).format(
                SimpleDateFormat(DATE_INPUT).parse(
                    publishedAt.toString().substring(0, 10)
                )
            ).toString()
        } else "",
        this
    )

fun ArticleSource.toArticleSourceUI(isSaved : Boolean = false): ArticleSourceUI =
    ArticleSourceUI(
        name, description, category, url, this, isSaved
    )

fun ArticleSourceUI.toArticleSource(): ArticleSource =
    ArticleSource(
        item.id,
        item.name,
        item.description,
        item.url,
        item.category,
        item.lang,
        item.country
    )

