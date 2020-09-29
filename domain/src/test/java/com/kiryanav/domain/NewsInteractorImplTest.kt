package com.kiryanav.domain

import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.model.NewsWrapper
import com.kiryanav.domain.model.SavedArticleWrapper
import com.kiryanav.domain.repoApi.ArticleRepository
import com.kiryanav.domain.repoApi.NewsRepository
import com.kiryanav.domain.repoApi.SourceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class NewsInteractorImplTest {

    @Mock
    lateinit var newsRepository: NewsRepository

    @Mock
    lateinit var articleRepository: ArticleRepository

    @Mock
    lateinit var sourceRepository: SourceRepository

    var mockArticle: Article = Article(
        "", "", "", "",
        "", "", "", "", ""
    )


    lateinit var newsInteractor: NewsInteractorImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        newsInteractor = NewsInteractorImpl(newsRepository, articleRepository, sourceRepository)
    }

    @Test
    fun getNews() {
        println("Get news")
        runBlocking {
            `when`(
                newsInteractor.getNews(
                    null,
                    null,
                    null,
                    newsInteractor.getSavedSources()
                )
            ).thenReturn(getFakeNews())
        }
    }

    @Test
    fun getSources() {
    }

    @Test
    fun deleteArticle() {
    }

    @Test
    fun saveArticle() {
    }

    @Test
    fun getSavedArticles() {
    }

    @Test
    fun getSourcesByLanguage() {
    }

    @Test
    fun getSavedSources() {
    }

    @Test
    fun saveSources() {
    }

    @Test
    fun deleteSource() {
    }

    private fun getFakeNews(): NewsWrapper {
        return NewsWrapper(
            2,
            listOf(
                SavedArticleWrapper(true, mockArticle),
                SavedArticleWrapper(false, mockArticle)
            )
        )
    }
}