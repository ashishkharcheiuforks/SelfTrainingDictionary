package com.san4o.just4fun.selftrainingdictionary.domain

import com.san4o.just4fun.selftrainingdictionary.domain.quiz.CurrentIrregularVerbQuiz
import com.san4o.just4fun.selftrainingdictionary.domain.quiz.IrregularQuizVerb
import com.san4o.just4fun.selftrainingdictionary.domain.quiz.IrregularQuizVerbResult
import ru.sportmaster.android.driven.salespoint.domain.base.result.Result

interface IrregularVerbQuizRepository {
    suspend fun currentQuiz(): Result<CurrentIrregularVerbQuiz>
    suspend fun findVerbQuizItems(): List<IrregularQuizVerbResult>
    suspend fun createQuiz(verbs: List<IrregularQuizVerb>): Long
    suspend fun findQuizVerbs(quizId: Long): List<IrregularVerbQuizItem>

    suspend fun updateQuizItemCompleted(quizId: Long, verbId: Long)
    suspend fun updateQuizItemError(quizId: Long, verbId: Long)
    suspend fun finishQuiz(quizId: Long)
    suspend fun wordsList(): List<IrregularVerbListItem>
    suspend fun findQuizErrorWords(quizId: Long): Result<List<IrregularVerb>>
    suspend fun updateQuizCurrentQuestion(quizId: Long, index: Int)
    suspend fun findQuiz(quizId: Long): IrregularVerbQuiz?

}