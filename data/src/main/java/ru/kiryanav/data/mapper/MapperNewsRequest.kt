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
import ru.kiryanav.data.dto.news.response.SortByForApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class MapperNewsRequest{

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
            if (from.isEmpty() && dayOffset == Constants.ZERO_INT) {
                getCurrentDate()
            } else if (dayOffset != Constants.ZERO_INT) {
                getDate(dayOffset)
            } else {
                from
            },
            if (to.isEmpty()) getDate(dayOffset + 1) else to,
            if (language.isEmpty()) Locale.getDefault().language else language,
            when (sortBy) {
                SortBy.POPULARITY -> SortByForApi.POPULARITY
                SortBy.RELEVANCY -> SortByForApi.RELEVANCY
                SortBy.PUBLISHED_AT -> SortByForApi.PUBLISHED_AT
            }
        )




    // TIME ZONE FIX, PARSE FIX
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