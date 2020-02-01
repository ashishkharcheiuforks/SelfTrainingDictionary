package com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quizwrite

import androidx.lifecycle.ViewModel

class IrregularVerbWriteQuizResultViewModel(
    val rightCount: Int,
    val wrongCount: Int
) : ViewModel() {

    val percent: Int = ((rightCount.toDouble() / (rightCount + wrongCount)) * 100).toInt()
}