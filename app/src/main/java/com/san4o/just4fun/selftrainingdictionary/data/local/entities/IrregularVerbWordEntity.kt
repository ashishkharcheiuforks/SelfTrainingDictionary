package com.san4o.just4fun.selftrainingdictionary.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "irregular_verb_word")
data class IrregularVerbWordEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Long = 0,

    @ColumnInfo
    val present: String,
    @ColumnInfo
    val past: String,
    @ColumnInfo
    val perfect: String,

    @ColumnInfo
    val translation: String
)