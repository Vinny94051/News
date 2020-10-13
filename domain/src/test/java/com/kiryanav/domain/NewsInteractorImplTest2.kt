package com.kiryanav.domain


import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import vlnny.base.data.model.successValue
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NewsInteractorImplTest2 {

    companion object {
        const val NEWS_REPO_SOURCES_NUMBER = 15
        const val SOURCE_REPO_SOURCES_NUMBER = 6
        const val NEWS_REPO_ARTICLES_NUMBER = 6
        const val ARTICLE_REPO_SOURCES_NUMBER = 8
    }

    private val newsRepo = FakeNewsRepository(NEWS_REPO_SOURCES_NUMBER, NEWS_REPO_ARTICLES_NUMBER)
    private val sourceRepo = FakeSourceRepository(SOURCE_REPO_SOURCES_NUMBER)
    private val articleRepo = FakeArticleRepository(ARTICLE_REPO_SOURCES_NUMBER)

    private val interactor = NewsInteractorImpl(newsRepo, articleRepo, sourceRepo)

    @Test
    fun getNews() {
        runBlocking {
            val news = interactor.getNews(
                sources = interactor.getSavedSources().successValue()!!
            ).successValue()
            assertEquals(NEWS_REPO_ARTICLES_NUMBER, news?.articles?.size!! - 1)
        }
    }


    @Test
    fun getSources() {
        runBlocking {
            val sources = interactor.getSources().successValue()

            val savedSourcesCounter = sources?.count { wrapped ->
                wrapped.isSelected
            }

            assertEquals(
                savedSourcesCounter,
                SOURCE_REPO_SOURCES_NUMBER
            )

        }
    }

    @Test
    fun getSavedArticles() {
        runBlocking {
            val savedArticles = interactor.getSavedArticles().successValue()
            assertEquals(SOURCE_REPO_SOURCES_NUMBER, savedArticles?.size!! - 1)
        }
    }
}