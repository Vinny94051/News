package ru.kiryanav.news.data.dto.source

import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    val id : String?,
    @SerializedName("name")
    val name : String,
    @SerializedName("description")
    val description : String?,
    @SerializedName("url")
    val url : String,
    @SerializedName("category")
    val category : String?,
    @SerializedName("language")
    val lang : String?,
    @SerializedName("country")
    val country : String?
)