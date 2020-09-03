package ru.kiryanav.ui.mapper

import android.content.Context
import com.kiryanav.domain.model.Article
import ru.kiryanav.ui.R
import ru.kiryanav.ui.model.ArticleUI

fun ArticleUI.toArticle(): Article =
    item

fun Article.toArticleUI(context: Context): ArticleUI =
    ArticleUI(
        if (author.isNullOrEmpty()) {
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
        publishedAt.orEmpty(),
        this
    )

