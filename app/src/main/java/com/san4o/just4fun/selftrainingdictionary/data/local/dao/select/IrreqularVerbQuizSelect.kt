package com.san4o.just4fun.selftrainingdictionary.data.local.dao.select

import androidx.room.Embedded
import androidx.room.Relation
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbQuizEntity
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbQuizItemEntity

data class IrreqularVerbQuizSelect(
    @Embedded
    val entity: IrregularVerbQuizEntity,

    @Relation(
        entity = IrregularVerbQuizItemEntity::class,
        entityColumn = IrregularVerbQuizItemEntity.COLUMN_QUIZ_ID,
        parentColumn = "id"
    )
    val words: List<IrregularVerbQuizItemEntity>
)