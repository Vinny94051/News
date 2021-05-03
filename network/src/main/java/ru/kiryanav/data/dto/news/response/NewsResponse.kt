package ru.kiryanav.data.dto.news.response

import com.google.gson.annotations.SerializedName
import ru.kiryanav.data.dto.news.response.Article

data class NewsResponse(
    @SerializedName("status")
    val status : String?,
    @SerializedName("totalResults")
    val resultsNumber : Long?,
    @SerializedName("articles")
    val articles : List<Article>
)