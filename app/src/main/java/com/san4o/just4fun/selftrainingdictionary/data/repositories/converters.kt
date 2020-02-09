package com.san4o.just4fun.selftrainingdictionary.data.repositories

import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbWordEntity
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerb

internal fun IrregularVerbWordEntity.toIrregularVerb(): IrregularVerb {
    val entity = this
    return IrregularVerb(
        id = entity.id,
        translation = entity.translation,
        present = entity.present,
        past = entity.past,
        perfect = entity.perfect
    )
}
