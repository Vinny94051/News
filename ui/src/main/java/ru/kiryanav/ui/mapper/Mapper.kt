@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package ru.kiryanav.ui.mapper

import android.annotation.SuppressLint
import android.content.Context
import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.model.SavedArticleSourceWrapper
import com.kiryanav.domain.model.SavedArticleWrapper
import ru.kiryanav.ui.R
import ru.kiryanav.ui.model.ArticleItem
import ru.kiryanav.ui.model.ArticleSourceUI
import java.text.SimpleDateFormat
import java.util.*

fun ArticleItem.ArticleUI.toArticle(): Article =
    item

private const val DATE_OUTPUT = "dd.MM.yyyy"


@SuppressLint("SimpleDateFormat")
fun SavedArticleWrapper.toArticleUI(context: Context): ArticleItem.ArticleUI =
    ArticleItem.ArticleUI(
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
        createDate(item.publishedAt),
        item,
        isSaved
    )

@SuppressLint("SimpleDateFormat")
private fun createDate(date: Date?): String {
    val sff = SimpleDateFormat(DATE_OUTPUT)
    return sff.format(date)
}

fun List<SavedArticleWrapper>.toArticleItemList(context: Context): List<ArticleItem> {
    val resultList = mutableListOf<ArticleItem>()
    val tmpList: List<ArticleItem.ArticleUI> = this.map { articleWrapper ->
        articleWrapper.toArticleUI(context)
    }
    if (tmpList.isNotEmpty()) {
        resultList.add(ArticleItem.DateHeader(tmpList[0].publishedAt))

        for (i in 0..tmpList.size - 2) {
            resultList.add(tmpList[i])
            if (tmpList[i].publishedAt != tmpList[i + 1].publishedAt) {
                resultList.add(ArticleItem.DateHeader(tmpList[i + 1].publishedAt))
            }
        }
        resultList.add(tmpList[tmpList.size - 1])
    }
    return resultList
}


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

