package com.san4o.just4fun.selftrainingdictionary.data.local.converters

import androidx.room.TypeConverter
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbQuizItemState

class IrregularVerbQuizResultConverter {
    @TypeConverter
    fun toDate(value: String?): IrregularVerbQuizItemState? = if (value == null) {
        null
    } else {
        IrregularVerbQuizItemState.valueOf(value)
    }

    @TypeConverter
    fun toFormat(value: IrregularVerbQuizItemState?): String? = if (value == null) {
        null
    } else {
        value.name
    }
}