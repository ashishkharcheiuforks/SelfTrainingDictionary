package com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quiz

import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbItem

class IrregularVerbsQuizQuestionBuilder(
    private val words: List<IrregularVerbItem>
) {

    fun questions(): List<IrregularVerbsQuizQuestion> {
        return words.map { item ->

            createQuestion(item)
        }
    }

    private fun createQuestion(item: IrregularVerbItem): IrregularVerbsQuizQuestion {
        val present = item.present

        val random = UniqueQuestionFromList(words.map { it.present }, present)

        return IrregularVerbsQuizQuestion(
            valid = present,

            answer1 = random.next(),
            answer2 = random.next(),
            answer3 = random.next(),
            answer4 = random.next()
        )
    }

}

