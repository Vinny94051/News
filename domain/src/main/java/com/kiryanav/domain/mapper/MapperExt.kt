package com.kiryanav.domain.mapper

import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.News
import ru.kiryanav.ui.model.ArticleUI
import ru.kiryanav.ui.model.NewsUI

fun Article.toUIModel(): ArticleUI =
    ArticleUI(
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

fun News.toUIModel(): NewsUI =
    NewsUI(
        resultNumber,
        articles.map { article ->
            article.toUIModel()
        }
    )

fun ArticleUI.toDomain() : Article =
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