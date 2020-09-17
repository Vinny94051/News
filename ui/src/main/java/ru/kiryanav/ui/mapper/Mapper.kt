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

fun ArticleItem.ArticleUI.toArticle(): Article =
    item

private const val DATE_INPUT = "yyyy-MM-dd"
private const val DATE_OUTPUT = "dd.MM.yyyy"

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
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
        if (!item.publishedAt.isNullOrEmpty()) {
            createDate(item.publishedAt.toString())
        } else "",
        item,
        isSaved
    )

@SuppressLint("SimpleDateFormat")
private fun createDate(date: String): String {
    return SimpleDateFormat(DATE_OUTPUT).format(
        SimpleDateFormat(DATE_INPUT).parse(
            date.substring(0, 10)
        )
    ).toString()
}

fun List<SavedArticleWrapper>.toArticleItemList(context: Context): List<ArticleItem> {
    val resultList = mutableListOf<ArticleItem>()
    val tmpList : List<ArticleItem.ArticleUI> = this.map {
        it.toArticleUI(context)
    }

    resultList.add(ArticleItem.DateHeader(tmpList[0].publishedAt))

    for(i in 0..tmpList.size-2){
        resultList.add(tmpList[i])
        if(tmpList[i].publishedAt != tmpList[i+1].publishedAt){
            resultList.add(ArticleItem.DateHeader(tmpList[i+1].publishedAt))
        }
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

