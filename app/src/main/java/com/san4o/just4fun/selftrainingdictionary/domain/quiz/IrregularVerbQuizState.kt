package com.san4o.just4fun.selftrainingdictionary.domain.quiz

import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerb

data class IrregularVerbQuizState(
    val verbs: List<IrregularVerb>,
    val startPosition: Int,
    val successes: Int,
    val errors: Int
)