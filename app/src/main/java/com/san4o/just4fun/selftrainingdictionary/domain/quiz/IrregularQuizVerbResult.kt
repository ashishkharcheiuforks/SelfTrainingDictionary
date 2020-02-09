package com.san4o.just4fun.selftrainingdictionary.domain.quiz

data class IrregularQuizVerbResult(
    val verbId: Long,
    val error: Int,
    val success: Int,
    val count: Int
) {
}