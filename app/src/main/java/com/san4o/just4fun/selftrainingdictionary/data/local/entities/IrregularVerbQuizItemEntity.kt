package com.san4o.just4fun.selftrainingdictionary.data.local.entities

import androidx.room.*
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbQuizItemEntity.Companion.COLUMN_QUIZ_ID
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbQuizItemEntity.Companion.COLUMN_VERB_ID
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbQuizItemState

@Entity(
    tableName = "irregular_verb_quiz_item",
    foreignKeys = [
        ForeignKey(
            entity = IrregularVerbQuizEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf(COLUMN_QUIZ_ID),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = IrregularVerbWordEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf(COLUMN_VERB_ID),
            onDelete = ForeignKey.CASCADE
        )],
    indices = [
        Index(
            name = "idx_irregular_verb_quiz_item__quiz_id",
            value = [COLUMN_QUIZ_ID]
        ),
        Index(
            name = "idx_irregular_verb_quiz_item__verb_id",
            value = [COLUMN_VERB_ID]
        )
    ]
)
data class IrregularVerbQuizItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Long = 0,

    @ColumnInfo
    val position: Int,

    @ColumnInfo(name = COLUMN_VERB_ID)
    val verbId: Long,

    @ColumnInfo(name = COLUMN_QUIZ_ID)
    val quizId: Long,

    @ColumnInfo
    val state: IrregularVerbQuizItemState = IrregularVerbQuizItemState.NONE
) {
    companion object {
        const val COLUMN_QUIZ_ID = "quiz_id"
        const val COLUMN_VERB_ID = "verb_id"
    }
}

fun IrregularVerbQuizItemEntity.isSuccessResult(): Boolean {
    return this.state == IrregularVerbQuizItemState.SUCCESS
}

fun IrregularVerbQuizItemEntity.isErrorResult(): Boolean {
    return this.state == IrregularVerbQuizItemState.ERROR
}

fun IrregularVerbQuizItemEntity.isWithResult(): Boolean {
    return this.isErrorResult() || isSuccessResult()
}
