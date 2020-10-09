package ru.kiryanav.ui.presentation.fragment.news.selected

import android.content.Context
import com.kiryanav.domain.model.Article
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import ru.kiryanav.ui.R
import ru.kiryanav.ui.model.ArticleItem
import ru.kiryanav.ui.presentation.fragment.news.current.FakeInteractor
import java.util.*

internal class SelectedNewsViewModelTest {

    private val mockContext = mockk<Context>()
    private val newsInteractor = FakeInteractor()
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
    fun remove() {
        viewModel.remove(
            ArticleItem.ArticleUI(
                "",
                "",
                "",
                "",
                "",
                "",
                Article(
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    Date(),
                    ""
                ),
                true
            )
        )
    }

    @Test
    fun getSavedArticles() {
        viewModel.getSavedArticles()
    }
}