package com.san4o.just4fun.selftrainingdictionary.data.local.converters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDate(time: Long?): Date? = if (time == null) {
        null
    } else {
        Date(time)
    }

    @TypeConverter
    fun toFormat(date: Date?): Long? = if (date == null) {
        null
    } else {
        date.time
    }
}