package com.kiryanav.domain


import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import vlnny.base.data.model.successValue
import kotlin.test.assertNotNull


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NewsInteractorImplTest2 {

    private val newsRepo = FakeNewsRepository()
    private val sourceRepo = FakeSourceRepository()
    private val articleRepo = FakeArticleRepository()

    private val interactor = NewsInteractorImpl(newsRepo, articleRepo, sourceRepo)

    @Test
    fun getNews() {
        runBlocking {
            assertNotNull(
                interactor.getNews(
                    null, null, null,
                    interactor.getSavedSources().successValue()!!
                ).successValue()
            )
        }
    }

    @Test
    fun getSources() {
        runBlocking {
            assertNotNull(
                interactor.getSources().successValue()
            )
        }
    }

    @Test
    fun getSavedArticles(){
        runBlocking {
            assertNotNull(
                interactor.getSources().successValue()
            )
        }
    }
}