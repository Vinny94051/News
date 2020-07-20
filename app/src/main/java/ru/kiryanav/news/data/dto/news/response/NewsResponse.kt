package ru.kiryanav.news.data.dto.news.response

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status")
    val status : String?,
    @SerializedName("totalResults")
    val resultsNumber : Long?,
    @SerializedName("articles")
    val articles : List<Article>
)