package ru.kiryanav.news.data.mapper

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ru.kiryanav.news.App
import ru.kiryanav.news.Constants
import ru.kiryanav.news.R
import ru.kiryanav.news.data.dto.news.request.NewsRequest
import ru.kiryanav.news.data.dto.news.response.NewsResponse
import ru.kiryanav.news.domain.model.ArticleUI
import ru.kiryanav.news.domain.model.NewsUIModel
import ru.kiryanav.news.domain.model.SortBy
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import javax.inject.Inject

class MapperEverythingRequest : IMapper<NewsResponse, NewsUIModel> {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var context: Context

    @RequiresApi(Build.VERSION_CODES.O)
    override fun mapFromEntity(input: NewsResponse): NewsUIModel =
        NewsUIModel(
            context.getString(R.string.total_results)
                .format(input.resultsNumber?.toInt() ?: Constants.EMPTY_INT),
            input.articles.map { article ->
                ArticleUI(
                    article.source.id ?: Constants.EMPTY_STRING,
                    article.source.name ?: Constants.EMPTY_STRING,
                    context.getString(R.string.author)
                        .format(article.author ?: context.getString(R.string.unknown_author)),
                    article.title ?: Constants.EMPTY_STRING,
                    article.description ?: Constants.EMPTY_STRING,
                    article.articleUrl ?: Constants.EMPTY_STRING,
                    article.previewUrl ?: Constants.EMPTY_STRING,
                    mapDate(article.publishedAt ?: Constants.EMPTY_STRING), //Todo
                    article.content ?: Constants.EMPTY_STRING
                )
            }
        )

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    private fun mapDate(date: String): String =
        Instant.parse(date).toString()

    //TODO
    fun mapToEntity(
        query: String,
        from: String,
        to: String,
        language: String,
        sortBy: SortBy
    ): NewsRequest =
        NewsRequest(
            query,
            if (from == Constants.EMPTY_STRING) getDate() else from,
            if (to == Constants.EMPTY_STRING) getSDate() else to,
            if (language == Constants.EMPTY_STRING) Locale.getDefault().language else language,
            sortBy
        )

    @SuppressLint("SimpleDateFormat")
    private fun getDate(): String {
        val tz = TimeZone.getTimeZone("UTC")
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")
        df.timeZone = tz
        return df.format(Date()).toString()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getSDate(): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        return dateFormat.format(cal.time).toString()
    }
}