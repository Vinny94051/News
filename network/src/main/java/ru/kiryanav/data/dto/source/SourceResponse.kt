package ru.kiryanav.data.dto.source

import com.google.gson.annotations.SerializedName
import ru.kiryanav.data.dto.source.Source

data class SourceResponse(
    @SerializedName("status")
    val status : String?,
    @SerializedName("sources")
    val sources : List<Source>
)