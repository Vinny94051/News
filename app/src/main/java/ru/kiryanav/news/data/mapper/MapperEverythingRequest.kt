package ru.kiryanav.news.data.mapper

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
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
import java.util.*
import javax.inject.Inject

class MapperEverythingRequest(private val context : Context) : IMapper<NewsResponse, NewsUIModel> {

    companion object {
        private const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'"
    }

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
                    mapDate(article.publishedAt ?: Constants.EMPTY_STRING),
                    article.content ?: Constants.EMPTY_STRING
                )
            }
        )

    private fun mapDate(date: String): String =
        Instant.parse(date).toString()

    fun mapToEntity(
        query: String,
        from: String,
        to: String,
        language: String,
        sortBy: SortBy,
        dayNumber: Int
    ): NewsRequest =
        NewsRequest(
            query,
            if (from == Constants.EMPTY_STRING && dayNumber == Constants.ZERO_INT)
                getCurrentDate()
            else if (dayNumber != Constants.ZERO_INT) getDate(
                dayNumber
            ) else from,
            if (to == Constants.EMPTY_STRING) getDate(dayNumber, true) else to,
            if (language == Constants.EMPTY_STRING) Locale.getDefault().language else language,
            sortBy
        )

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val tz = TimeZone.getTimeZone("UTC")
        val df: DateFormat =
            SimpleDateFormat(ISO_DATE_FORMAT)
        df.timeZone = tz
        return df.format(Date())
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate(dayNumber: Int, isSecondDate: Boolean = false): String {
        val dateFormat = SimpleDateFormat(ISO_DATE_FORMAT)
        val myDate = dateFormat.parse(getCurrentDate()) ?: return Constants.EMPTY_STRING
        val dif : Long =
            if (isSecondDate)
                ((dayNumber + 1) * 24L * 60L * 60L * 1000L)
            else ((dayNumber) * 24L * 60L * 60L * 1000L)
        val newDate = Date(myDate.time - dif) // n * 24 * 60 * 60 * 1000
        return dateFormat.format(newDate)

    }
}