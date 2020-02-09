package com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quiz

import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbListItem

class IrregularVerbsQuizQuestionBuilder(
    private val words: List<IrregularVerbListItem>
) {

    fun questions(): List<IrregularVerbsQuizQuestion> {
        return words.map { item ->

            createQuestion(item)
        }
    }

    private fun createQuestion(item: IrregularVerbListItem): IrregularVerbsQuizQuestion {
        val present = item.verb.present

        val random = UniqueQuestionFromList(words.map { it.verb.present }, present)

        return IrregularVerbsQuizQuestion(
            valid = present,

            answer1 = random.next(),
            answer2 = random.next(),
            answer3 = random.next(),
            answer4 = random.next()
        )
    }

}

