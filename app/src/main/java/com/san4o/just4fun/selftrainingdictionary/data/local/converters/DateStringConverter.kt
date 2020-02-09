package com.san4o.just4fun.selftrainingdictionary.data.local.converters

import androidx.room.TypeConverter
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class DateStringConverter {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    @TypeConverter
    fun toDate(dateInFormat: String?): Date? = if (dateInFormat == null) {
        null
    } else {
        try {
            dateFormat.parse(dateInFormat)
        } catch (e: Exception) {
            Timber.e(e, "room field format $dateInFormat")
            null
        }

    }

    @TypeConverter
    fun toFormat(date: Date?): String? = if (date == null) {
        null
    } else {
        dateFormat.format(date)
    }
}