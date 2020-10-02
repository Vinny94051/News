package ru.kiryanav.ui.presentation.fragment.news.current

import android.content.Context
import com.kiryanav.domain.model.Article
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import ru.kiryanav.ui.R
import ru.kiryanav.ui.model.ArticleItem
import kotlin.test.assertNotEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class NewsViewModelTest {

    private val mockContext = mockk<Context>()
    private val newsInteractor = FakeInteractor()
    private var viewModel: NewsViewModel = NewsViewModel(mockContext, newsInteractor)

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
    open fun removeArticle() {
        viewModel.removeArticle(
            ArticleItem.ArticleUI(
                "",
                "",
                "", "",
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
                    "",
                    ""
                ),
                true
            )
        )
        assertNotEquals(viewModel.isWithUnknownError, true)
    }


    @Test
    open fun loadNews() {
        viewModel.loadNews()
        assertNotEquals(viewModel.isWithUnknownError, true)
    }

    @Test
    open fun loadMore() {
        viewModel.loadMore()
        assertNotEquals(viewModel.isWithUnknownError, true)
    }

    @Test
    open fun saveArticle() {
    }
}