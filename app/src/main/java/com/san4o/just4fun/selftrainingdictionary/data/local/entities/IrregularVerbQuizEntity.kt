package com.san4o.just4fun.selftrainingdictionary.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(
    tableName = "irregular_verb_quiz"
)
data class IrregularVerbQuizEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Long = 0,

    @ColumnInfo(name = "current_position")
    val currentPosition: Int = 0,

    @ColumnInfo
    val start: Date,
    @ColumnInfo
    val startDateInFormat: String,
    @ColumnInfo
    val complete: Date? = null,
    @ColumnInfo
    val completeDateInFormat: String? = null
) {
    companion object {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

        fun newQuiz(date: Date): IrregularVerbQuizEntity {
            return IrregularVerbQuizEntity(
                start = date,
                startDateInFormat = dateFormat.format(date)
            )
        }
    }
}