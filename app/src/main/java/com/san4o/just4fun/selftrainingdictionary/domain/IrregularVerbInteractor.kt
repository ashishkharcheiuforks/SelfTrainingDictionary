package com.san4o.just4fun.selftrainingdictionary.domain

import com.san4o.just4fun.selftrainingdictionary.domain.quiz.IrregularVerbQuizState
import ru.sportmaster.android.driven.salespoint.domain.base.result.CompleteResult
import ru.sportmaster.android.driven.salespoint.domain.base.result.Result
import ru.sportmaster.android.driven.salespoint.domain.base.result.runComplete
import ru.sportmaster.android.driven.salespoint.domain.base.result.runSuccess
import javax.inject.Inject

class IrregularVerbInteractor @Inject constructor(
    private val quizRepository: IrregularVerbQuizRepository
) {


    suspend fun answerResult(quizId: Long, verbId: Long, success: Boolean) {
        if (success) {
            quizRepository.updateQuizItemCompleted(quizId, verbId)
        } else {
            quizRepository.updateQuizItemError(quizId, verbId)
        }
    }

    suspend fun findQuiz(quizId: Long): Result<IrregularVerbQuizState> = runSuccess {
        val quiz = quizRepository.findQuiz(quizId)!!
        val startPosition = quiz.position
        val verbs = quizRepository.findQuizVerbs(quizId)
        val quizVerbs = verbs.sortedBy { it.position }
        var success = 0
        var error = 0
        for (quizVerb in quizVerbs) {
            if (quizVerb.position == startPosition) {
                break
            }
            when (quizVerb.state) {
                IrregularVerbQuizItemState.ERROR -> error++
                IrregularVerbQuizItemState.SUCCESS -> success++
                IrregularVerbQuizItemState.NONE -> TODO()
            }
        }
        return@runSuccess IrregularVerbQuizState(
            verbs = quizVerbs.map { it.verb },
            startPosition = startPosition,
            successes = success,
            errors = error
        )
    }

    suspend fun finishQuiz(quizId: Long) {
        quizRepository.finishQuiz(quizId)
    }

    suspend fun findQuizErrorWords(quizId: Long): Result<List<IrregularVerb>> {
        return quizRepository.findQuizErrorWords(quizId)
    }


    suspend fun updateQuizCurrentQuestion(quizId: Long, index: Int): CompleteResult = runComplete {
        quizRepository.updateQuizCurrentQuestion(quizId, index)

    }

}