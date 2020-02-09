package com.san4o.just4fun.selftrainingdictionary.domain

data class IrregularVerbQuizItem(
    val verb: IrregularVerb,

    val position: Int,
    val state: IrregularVerbQuizItemState
) {
}

enum class IrregularVerbQuizItemState {
    NONE,
    SUCCESS,
    ERROR
}