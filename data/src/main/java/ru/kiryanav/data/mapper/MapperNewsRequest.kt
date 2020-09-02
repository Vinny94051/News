package ru.kiryanav.data.mapper

import android.annotation.SuppressLint
import android.content.Context
import com.kiryanav.domain.Constants
import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.News
import com.kiryanav.domain.model.SortBy
import ru.kiryanav.data.R
import ru.kiryanav.data.dto.news.request.NewsRequest
import ru.kiryanav.data.dto.news.response.NewsResponse
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class MapperNewsRequest(private val context: Context) {

    companion object {
        private const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'"
    }

    fun mapToEntity(
        query: String,
        from: String,
        to: String,
        language: String,
        sortBy: SortBy,
        dayOffset: Int
    ): NewsRequest =
        NewsRequest(
            query,
            if (from == Constants.EMPTY_STRING && dayOffset == Constants.ZERO_INT)
                getCurrentDate()
            else if (dayOffset != Constants.ZERO_INT)
                getDate(dayOffset) else
                from,
            if (to == Constants.EMPTY_STRING) getDate(dayOffset + 1) else to,
            if (language == Constants.EMPTY_STRING) Locale.getDefault().language else language,
            sortBy
        )

    fun mapFromEntity(
        input: NewsResponse
    ) : News {
        return News(
            context.getString(R.string.total_results)
                .format(input.resultsNumber?.toInt() ?: Constants.EMPTY_INT),
            input.articles.map { article ->
                Article(
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
            })
    }

    private fun mapDate(date: String): String =
        Instant.parse(date).toString()

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val tz = TimeZone.getTimeZone("UTC")
        val df: DateFormat = SimpleDateFormat(ISO_DATE_FORMAT)
        df.timeZone = tz
        return df.format(Date())
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate(dayNumber: Int): String {
        val dateFormat = SimpleDateFormat(ISO_DATE_FORMAT)
        val myDate = dateFormat.parse(getCurrentDate()) ?: return Constants.EMPTY_STRING
        val dif: Long = dayNumber * 24L * 60L * 60L * 1000L
        val newDate = Date(myDate.time - dif) // n * 24 * 60 * 60 * 1000
        return dateFormat.format(newDate)

    }
}