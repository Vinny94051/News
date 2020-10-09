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
        const val NEWS_REPO_SOURCES_NUMBER = 9
        const val SOURCE_REPO_SOURCES_NUMBER = 4
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
            assertEquals(NEWS_REPO_ARTICLES_NUMBER, news?.articles?.size!!-1)
        }
    }


    @Test
    fun getSources() {
        runBlocking {
            val sources = interactor.getSources().successValue()
            var savedSourcesCounter = 0

            sources?.forEach { wrapped ->
                if (wrapped.isSelected) {
                    savedSourcesCounter++
                }
            }

            assertEquals(
                savedSourcesCounter,
                NEWS_REPO_SOURCES_NUMBER - SOURCE_REPO_SOURCES_NUMBER
            )

        }
    }

    internal class InvalidDataException(message: String) : Throwable(message)

    @Test
    fun getSavedArticles() {
        runBlocking {
            val savedArticles = interactor.getSavedArticles().successValue()
            assertEquals(SOURCE_REPO_SOURCES_NUMBER, savedArticles?.size!! - 1)
        }
    }
}