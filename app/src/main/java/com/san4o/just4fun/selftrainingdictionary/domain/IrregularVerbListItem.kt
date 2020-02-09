package com.san4o.just4fun.selftrainingdictionary.domain

data class IrregularVerbListItem(
    val verb: IrregularVerb,

    val wrong: Int,
    val correct: Int
) {
}