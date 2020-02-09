package com.san4o.just4fun.selftrainingdictionary.domain

import com.san4o.just4fun.selftrainingdictionary.domain.quiz.CurrentIrregularVerbQuiz
import com.san4o.just4fun.selftrainingdictionary.domain.quiz.IrregularQuizVerb
import ru.sportmaster.android.driven.salespoint.domain.base.result.Result
import ru.sportmaster.android.driven.salespoint.domain.base.result.runResult
import ru.sportmaster.android.driven.salespoint.domain.base.result.runSuccess
import javax.inject.Inject

class IrregularVersListInteractor @Inject constructor(

    private val quizRepository: IrregularVerbQuizRepository

) {
    suspend fun wordsList(): Result<List<IrregularVerbListItem>> = runSuccess {
        return@runSuccess quizRepository.wordsList()
    }


    suspend fun currentQuiz(): Result<CurrentIrregularVerbQuiz> {
        return quizRepository.currentQuiz()
    }

    suspend fun createQuiz(): Result<Long> {
        return runResult {

            val verbs = randomNewStudyingVerbs(2)

            return@runResult Result.Success(
                quizRepository.createQuiz(verbs)
            )
        }
    }

    private suspend fun randomNewStudyingVerbs(limit: Int): List<IrregularQuizVerb> {

        val verbQuizItems = quizRepository.findVerbQuizItems()

        val randomList = ArrayList<IrregularQuizVerb>()

        while (randomList.size < limit) {
            val random = verbQuizItems.random()
            val verbId = random.verbId
            if (randomList.anyVerb(verbId)) {
                continue
            }
            val min =
                verbQuizItems.filter { randomList.noneVerb(it.verbId) }
                    .map { it.error }
                    .min() ?: 0

            if (random.error == min) {
                randomList.add(IrregularQuizVerb(verbId))
            }
        }
        return randomList
    }
}

private fun List<IrregularQuizVerb>.noneVerb(verbId: Long): Boolean {
    return this.none { it.verbId == verbId }
}

private fun List<IrregularQuizVerb>.anyVerb(verbId: Long): Boolean {
    return this.any { it.verbId == verbId }
}