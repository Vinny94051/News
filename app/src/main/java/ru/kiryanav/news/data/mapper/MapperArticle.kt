package ru.kiryanav.news.data.mapper

import ru.kiryanav.news.data.database.entites.ArticleEntity
import ru.kiryanav.news.domain.model.ArticleUI

class MapperArticle : IMapper<ArticleEntity, ArticleUI> {
    fun mapToEntity(input: ArticleUI) =
        ArticleEntity(
            input.sourceId,
            input.sourceName,
            input.author,
            input.title,
            input.description,
            input.articleUrl,
            input.previewImageUrl,
            input.publishedAt,
            input.content
        )

    fun mapFromListEntity(input : List<ArticleEntity>) =
        input.map {entity ->
            mapFromEntity(entity)
        }

    override fun mapFromEntity(input: ArticleEntity) =
        ArticleUI(
            input.sourceId,
            input.sourceName,
            input.author,
            input.title,
            input.description,
            input.articleUrl,
            input.previewImageUrl,
            input.publishedAt,
            input.content
        )
}