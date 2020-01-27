package com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quiz

import com.san4o.just4fun.selftrainingdictionary.presentation.UniqueRandomFromList

class UniqueQuestionFromList(
    words: List<String>,
    private val exclude: String
) {
    private val random =
        UniqueRandomFromList(
            words
        )

    fun next(): String {
        var next = random.next()
        if (exclude == next) {
            return random.next()
        }
        return next
    }
}