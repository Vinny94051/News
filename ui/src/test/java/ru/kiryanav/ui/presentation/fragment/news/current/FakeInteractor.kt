package ru.kiryanav.ui.presentation.fragment.news.current

import com.kiryanav.domain.FakeSourceRepository
import com.kiryanav.domain.FakeArticleRepository
import com.kiryanav.domain.FakeNewsRepository
import com.kiryanav.domain.NewsInteractor
import com.kiryanav.domain.NewsInteractorImpl
import com.kiryanav.domain.model.*
import com.kiryanav.domain.Error
import vlnny.base.data.model.ResponseResult

class FakeInteractor : NewsInteractor {

    private val newsInteractor =
        NewsInteractorImpl(FakeNewsRepository(), FakeArticleRepository(), FakeSourceRepository())

    override suspend fun getNews(
        query: String?,
        from: String?,
        to: String?,
        sources: List<ArticleSource>,
        language: String?,
        pageNumber: Int
    ): ResponseResult<NewsWrapper, Error> =
        newsInteractor.getNews(query, from, to, sources, language, pageNumber)

    override suspend fun saveArticle(article: Article) = returnUnitResult()

    override suspend fun getSavedArticles(): ResponseResult<List<SavedArticleWrapper>, Error> {
        return newsInteractor.getSavedArticles()
    }

    override suspend fun getSourcesByLanguage(language: String): ResponseResult<List<ArticleSource>, Error> {
        return newsInteractor.getSourcesByLanguage(language)
    }

    override suspend fun getSavedSources(): ResponseResult<List<ArticleSource>, Error> {
        return newsInteractor.getSavedSources()
    }

    override suspend fun saveSources(sources: List<ArticleSource>) = returnUnitResult()

    override suspend fun deleteSource(source: ArticleSource) = returnUnitResult()

    override suspend fun getSources(): ResponseResult<List<SavedArticleSourceWrapper>, Error> {
        return newsInteractor.getSources()
    }

    override suspend fun deleteArticle(article: Article) = returnUnitResult()

    private fun returnUnitResult() = ResponseResult.Success(Unit)
}