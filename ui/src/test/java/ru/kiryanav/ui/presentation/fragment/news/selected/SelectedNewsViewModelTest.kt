package ru.kiryanav.ui.presentation.fragment.news.selected

import android.content.Context
import com.kiryanav.domain.model.Article
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import ru.kiryanav.ui.R
import ru.kiryanav.ui.model.ArticleItem
import ru.kiryanav.ui.presentation.fragment.news.InstantExecutorExtension
import ru.kiryanav.ui.presentation.fragment.news.current.FakeInteractor
import java.util.*

@ExtendWith(InstantExecutorExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class SelectedNewsViewModelTest {

    companion object{
        const val SAVED_ARTICLE_NUMBER = 10
    }

    private val mockContext = mockk<Context>()
    private val newsInteractor = FakeInteractor(SAVED_ARTICLE_NUMBER)
    private var viewModel = SelectedNewsViewModel(newsInteractor, mockContext)


    @BeforeEach
    internal fun setUp() {
        every {
            mockContext.getString(R.string.total_results)
        } returns "Total results: %s"

        every {
            mockContext.getString(R.string.author)
        } returns "Author %s"
    }

    @Test
    fun getAllSavedArticles() {
        viewModel.getSavedArticles()
        assertEquals(SAVED_ARTICLE_NUMBER, viewModel.articlesLiveData.value?.size!!)
    }

}