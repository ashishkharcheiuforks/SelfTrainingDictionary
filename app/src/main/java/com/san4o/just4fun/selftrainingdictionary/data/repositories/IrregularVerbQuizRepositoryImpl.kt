package com.san4o.just4fun.selftrainingdictionary.data.repositories

import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsDao
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsQuizDao
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbQuizEntity
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.isErrorResult
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.isSuccessResult
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.isWithResult
import com.san4o.just4fun.selftrainingdictionary.domain.*
import com.san4o.just4fun.selftrainingdictionary.domain.quiz.CurrentIrregularVerbQuiz
import com.san4o.just4fun.selftrainingdictionary.domain.quiz.IrregularQuizVerb
import com.san4o.just4fun.selftrainingdictionary.domain.quiz.IrregularQuizVerbResult
import ru.sportmaster.android.driven.salespoint.domain.base.result.Result
import ru.sportmaster.android.driven.salespoint.domain.base.result.runNonNullResult
import ru.sportmaster.android.driven.salespoint.domain.base.result.runSuccess
import java.util.*

class IrregularVerbQuizRepositoryImpl(
    private val verbsDao: IrregularVerbsDao,
    private val quizDao: IrregularVerbsQuizDao
) : IrregularVerbQuizRepository {

    override suspend fun currentQuiz(): Result<CurrentIrregularVerbQuiz> = runNonNullResult {
        val select = quizDao.findLastNotCompleteQuiz()
        if (select == null) {
            return@runNonNullResult null
        }

        return@runNonNullResult CurrentIrregularVerbQuiz(
            id = select.entity.id,
            date = select.entity.start,
            current = select.words.count { it.isWithResult() },
            total = select.words.size
        )
    }

    override suspend fun findVerbQuizItems(): List<IrregularQuizVerbResult> {
        return quizDao.findVerbQuizItems()
            .map {
                IrregularQuizVerbResult(
                    verbId = it.entity.id,
                    error = it.quizItems.count { item -> item.isErrorResult() },
                    success = it.quizItems.count { item -> item.isSuccessResult() },
                    count = it.quizItems.size
                )
            }

    }


    override suspend fun createQuiz(verbs: List<IrregularQuizVerb>): Long {
        return quizDao.createQuiz(verbs)
    }

    override suspend fun findQuizVerbs(quizId: Long): List<IrregularVerbQuizItem> {
        val quizItems = quizDao.findVerbQuizItems(quizId)
        val quizVerbs = verbsDao.findVerbs(quizItems.map { it.verbId })
        return quizItems
            .map { quiz ->
                IrregularVerbQuizItem(
                    verb = quizVerbs.find { it.id == quiz.verbId }!!.toIrregularVerb(),
                    position = quiz.position,
                    state = quiz.state
                )
            }
    }


    override suspend fun updateQuizItemCompleted(quizId: Long, verbId: Long) {
        quizDao.updateQuizItemState(quizId, verbId, IrregularVerbQuizItemState.SUCCESS)
    }

    override suspend fun updateQuizItemError(quizId: Long, verbId: Long) {
        quizDao.updateQuizItemState(quizId, verbId, IrregularVerbQuizItemState.ERROR)
    }

    override suspend fun finishQuiz(quizId: Long) {
        val date = Date()
        quizDao.updateQuizEndDate(quizId, date, IrregularVerbQuizEntity.dateFormat.format(date))
    }

    override suspend fun wordsList(): List<IrregularVerbListItem> {
        return quizDao.findVerbQuizItems()
            .map {
                IrregularVerbListItem(
                    verb = it.entity.toIrregularVerb(),
                    wrong = it.quizItems.count { item -> item.isErrorResult() },
                    correct = it.quizItems.count { item -> item.isSuccessResult() }
                )
            }
    }

    override suspend fun findQuizErrorWords(quizId: Long): Result<List<IrregularVerb>> =
        runSuccess {
            return@runSuccess quizDao.findQuizWordsInState(quizId, IrregularVerbQuizItemState.ERROR)
                .map { it.toIrregularVerb() }
        }

    override suspend fun updateQuizCurrentQuestion(quizId: Long, index: Int) {
        quizDao.updateQuizCurrentQuestion(quizId, index)
    }

    override suspend fun findQuiz(quizId: Long): IrregularVerbQuiz? {
        val quiz = quizDao.findQuiz(quizId)
        return quiz
            ?.let {
                IrregularVerbQuiz(
                    id = it.id,
                    position = it.currentPosition
                )
            }
    }
}