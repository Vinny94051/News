package ru.kiryanov.database.converter

import androidx.room.TypeConverter
import java.util.*

class Converter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun toTimeStamp(date : Date?) : Long? {
        return date?.time?.toLong()
    }

}