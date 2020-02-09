package com.san4o.just4fun.selftrainingdictionary.domain.quiz

import java.util.*

data class CurrentIrregularVerbQuiz(
    val id: Long,
    val date: Date,
    val current: Int,
    val total: Int
)