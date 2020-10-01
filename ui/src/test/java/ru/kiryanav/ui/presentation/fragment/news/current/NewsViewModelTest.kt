package ru.kiryanav.ui.presentation.fragment.news.current

import android.content.Context
import com.kiryanav.domain.model.Article
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.kiryanav.ui.model.ArticleItem

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class NewsViewModelTest {

    @Mock
    private lateinit var mockContext: Context
    private val newsInteractor = FakeInteractor()
    private lateinit var viewModel: NewsViewModel

    @BeforeEach
    internal fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = NewsViewModel(mockContext, newsInteractor)
    }

    @Test
    fun removeArticle() {
        runBlocking {
            viewModel.removeArticle(
                ArticleItem.ArticleUI(
                    "",
                    "",
                    "", "",
                    "",
                    "",
                    Article("", "", "", "", "", "", "", "", ""), true
                )
            )
        }
    }

    @Test
    fun loadNews() {
    }

    @Test
    fun loadMore() {
    }

    @Test
    fun saveArticle() {
    }
}