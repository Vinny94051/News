package ru.kiryanav.ui.presentation.fragment.news.current

import android.content.Context
import androidx.lifecycle.Observer
import com.kiryanav.domain.model.Article
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import ru.kiryanav.ui.R
import ru.kiryanav.ui.model.ArticleItem
import ru.kiryanav.ui.presentation.fragment.news.InstantExecutorExtension
import vlnny.base.ext.findAllBySubType
import vlnny.base.ext.getDate
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(InstantExecutorExtension::class)
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
    open fun loadNews() {
        viewModel.loadNews()
        val size = viewModel.newsLiveData.value?.size!!
        println(size)
        assertEquals(true, size > 0)
    }


    @Test
    open fun loadMore() {

        viewModel.loadNews()
        for (i in NewsViewModel.DAY_NUMBER_DEFAULT_VALUE + 1 until NewsViewModel.MAX_DAYS_NUMBER) {
            viewModel.loadMore()

            println("${newsInteractor.from}, ${getDate(i - 1)}")
            println("${newsInteractor.to}, ${getDate(i)}")

            assertEquals(newsInteractor.from, getDate(i - 1))
            assertEquals(newsInteractor.to, getDate(i))

        }
    }
}