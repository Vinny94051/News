package ru.kiryanav.ui.presentation.fragment.news.current

import com.kiryanav.domain.NewsInteractor
import com.kiryanav.domain.model.*
import com.kiryanav.domain.error.NewsError
import com.kiryanav.domain.error.SourceError
import vlnny.base.data.model.ResponseResult
import java.text.SimpleDateFormat
import java.util.*

class FakeInteractor(private val savedArticleNumber : Int = 10) : NewsInteractor {

    var from: Date? = null
    var to: Date? = null

    override suspend fun getNews(
        query: String?,
        from: Date?,
        to: Date?,
        sources: List<ArticleSource>,
        language: String?,
        pageNumber: Int
    ): ResponseResult<NewsWrapper, NewsError> {
        this.from = from
        this.to = to
        return ResponseResult.Success(createFakeNewsWrapper())
    }

    override suspend fun saveArticle(article: Article) = returnUnitResult()

    override suspend fun getSavedArticles(): ResponseResult<List<SavedArticleWrapper>, NewsError> =
        ResponseResult.Success(createArticleWrappers(true))

    override suspend fun getSourcesByLanguage(language: String): ResponseResult<List<ArticleSource>, SourceError> =
        ResponseResult.Success(createFakeSources())


    override suspend fun getSavedSources(): ResponseResult<List<ArticleSource>, SourceError> =
        ResponseResult.Success(createFakeSources())

    override suspend fun saveSources(sources: List<ArticleSource>) = returnUnitResult()

    override suspend fun deleteSource(source: ArticleSource) = returnUnitResult()

    override suspend fun getSources(): ResponseResult<List<SavedArticleSourceWrapper>, SourceError> =
        ResponseResult.Success(createFakeWrappedSources())

    override suspend fun deleteArticle(article: Article) = returnUnitResult()

    private fun returnUnitResult() = ResponseResult.Success(Unit)

    private fun createFakeNewsWrapper() =
        NewsWrapper(
            10,
            createArticleWrappers(date = from ?: Date())
        )

    private var dayNumber = 1

    private fun createArticleWrappers(
        isSaved: Boolean = false,
        date: Date = Date()
    ): List<SavedArticleWrapper> {
        val savedArticleWrappers = mutableListOf<SavedArticleWrapper>()

        for (i in 0 until savedArticleNumber) {
            savedArticleWrappers.add(
                SavedArticleWrapper(
                    if (!isSaved) i % 2 == 1 else isSaved,
                    Article(
                        "sourceId".plus(i),
                        "sourceName".plus(i),
                        "author".plus(i),
                        "title".plus(i),
                        "description".plus(i),
                        "articleUrl".plus(i),
                        "imageUrl".plus(i),
                        date,
                        "content".plus(i)
                    )
                )

            )
        }
        dayNumber++
        return savedArticleWrappers
    }


    private fun createFakeSources(): List<ArticleSource> {
        val sources = mutableListOf<ArticleSource>()

        for (i in 0..10) {
            sources.add(
                ArticleSource(
                    "id".plus(i),
                    "name".plus(i),
                    "description".plus(i),
                    "url".plus(i),
                    "category".plus(i),
                    "lang".plus(i),
                    "country".plus(i)
                )
            )
        }

        return sources
    }

    private fun createFakeWrappedSources(isSaved: Boolean = false): List<SavedArticleSourceWrapper> {
        val sources = mutableListOf<SavedArticleSourceWrapper>()

        for (i in 0..10) {
            sources.add(
                SavedArticleSourceWrapper(
                    if (!isSaved) i % 2 == 1 else isSaved,
                    ArticleSource(
                        "id".plus(i),
                        "name".plus(i),
                        "description".plus(i),
                        "url".plus(i),
                        "category".plus(i),
                        "lang".plus(i),
                        "country".plus(i)
                    )
                )
            )
        }

        return sources
    }
}