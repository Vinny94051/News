package ru.kiryanav.ui.mapper

import android.annotation.SuppressLint
import android.content.Context
import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.model.SavedArticleSourceWrapper
import com.kiryanav.domain.model.SavedArticleWrapper
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
fun SavedArticleWrapper.toArticleUI(context: Context): ArticleUI =
    ArticleUI(
        if (!item.author.isNullOrEmpty()) {
            context.getString(R.string.author).format(
                item.author
            )
        } else {
            context.getString(R.string.unknown_author)
        },
        item.title.orEmpty(),
        item.description.orEmpty(),
        item.articleUrl.orEmpty(),
        item.previewImageUrl.orEmpty(),
        if (!item.publishedAt.isNullOrEmpty()) {
            SimpleDateFormat(DATE_OUTPUT).format(
                SimpleDateFormat(DATE_INPUT).parse(
                    item.publishedAt.toString().substring(0, 10)
                )
            ).toString()
        } else "",
        item,
        isSaved
    )

fun SavedArticleSourceWrapper.toArticleSourceUI() =
    ArticleSourceUI(
        item.name,
        item.description,
        item.url,
        item.category,
        item,
        isSelected
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

