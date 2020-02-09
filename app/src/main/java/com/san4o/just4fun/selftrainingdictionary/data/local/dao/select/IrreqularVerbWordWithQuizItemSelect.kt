package com.san4o.just4fun.selftrainingdictionary.data.local.dao.select

import androidx.room.Embedded
import androidx.room.Relation
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbQuizItemEntity
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbWordEntity

data class IrreqularVerbWordWithQuizItemSelect(
    @Embedded
    val entity: IrregularVerbWordEntity,

    @Relation(
        entity = IrregularVerbQuizItemEntity::class,
        entityColumn = IrregularVerbQuizItemEntity.COLUMN_VERB_ID,
        parentColumn = "id"
    )
    val quizItems: List<IrregularVerbQuizItemEntity>
)