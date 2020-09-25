package ru.kiryanav.ui.mapper

import android.content.Context
import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.SavedArticleWrapper
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import ru.kiryanav.ui.R

class MapperKtTest {

    @Mock
    lateinit var mockContext: Context
    private val article: Article = Article(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(mockContext.getString(R.string.author)).thenReturn("Author is %s")
        Mockito.`when`(mockContext.getString(R.string.unknown_author)).thenReturn("Unknown")
    }

    @Test
    fun toArticleItemList() {
        val list: List<SavedArticleWrapper> =
            listOf(
                SavedArticleWrapper(
                    true,
                    article
                ),

                SavedArticleWrapper(
                    true,
                    article
                ),
                SavedArticleWrapper(
                    false,
                    article
                ),
                SavedArticleWrapper(
                    false,
                    article
                )
            )
        list.toArticleItemList(mockContext)
    }
}