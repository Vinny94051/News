package ru.kiryanav.news.data.dto.news.response

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("source")
    val source : Source,
    @SerializedName("author")
    val author : String?,
    @SerializedName("title")
    val title : String?,
    @SerializedName("description")
    val description : String?,
    @SerializedName("url")
    val articleUrl : String?,
    @SerializedName("urlToImage")
    val previewUrl : String?,
    @SerializedName("publishedAt")
    val publishedAt : String?,
    @SerializedName("content")
    val content : String?
)
