package com.san4o.just4fun.selftrainingdictionary.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.select.IrreqularVerbQuizSelect
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.select.IrreqularVerbWordWithQuizItemSelect
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbQuizEntity
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbQuizItemEntity
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbWordEntity
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbQuizItemState
import com.san4o.just4fun.selftrainingdictionary.domain.quiz.IrregularQuizVerb
import java.util.*

@Dao
interface IrregularVerbsQuizDao {
    @Transaction
    suspend fun findLastNotCompleteQuiz(): IrreqularVerbQuizSelect? {
        val list = findNotCompleteQuizes()

        val quiz = list.maxBy { it.start } ?: return null
        val quizItems = findQuizItems(quiz.id)

        return IrreqularVerbQuizSelect(
            entity = quiz,
            words = quizItems
        )
    }

    @Query("select * from irregular_verb_quiz where complete is null")
    suspend fun findNotCompleteQuizes(): List<IrregularVerbQuizEntity>

    @Query("select * from irregular_verb_quiz_item where quiz_id = :quizId")
    suspend fun findQuizItems(quizId: Long): List<IrregularVerbQuizItemEntity>


    @Query("select * from irregular_verb_word")
    suspend fun findVerbQuizItems(): List<IrreqularVerbWordWithQuizItemSelect>

    @Query("select * from irregular_verb_quiz_item where verb_id in (:verbIds)")
    suspend fun findVerbQuizItems(verbIds: List<Long>): List<IrregularVerbQuizItemEntity>

    @Query("select * from irregular_verb_quiz_item where quiz_id = :quizId")
    suspend fun findVerbQuizItems(quizId: Long): List<IrregularVerbQuizItemEntity>

    @Transaction
    suspend fun createQuiz(verbs: List<IrregularQuizVerb>): Long {
        val quizId = insert(IrregularVerbQuizEntity.newQuiz(Date()))

        for ((index, verb) in verbs.withIndex()) {
            insert(
                IrregularVerbQuizItemEntity(
                    position = index,
                    verbId = verb.verbId,
                    quizId = quizId
                )
            )
        }
        return quizId
    }

    @Insert
    suspend fun insert(entity: IrregularVerbQuizEntity): Long

    @Insert
    suspend fun insert(entity: IrregularVerbQuizItemEntity)

    @Query("update irregular_verb_quiz_item set state = :state where quiz_id = :quizId and verb_id = :verbId")
    suspend fun updateQuizItemState(quizId: Long, verbId: Long, state: IrregularVerbQuizItemState)

    @Transaction
    suspend fun incrementQuizError(quizId: Long, verbId: Long) {
    }

    @Query(
        """update irregular_verb_quiz 
        set complete = :date, completeDateInFormat = :dateInFormat 
        where id = :quizId"""
    )
    suspend fun updateQuizEndDate(quizId: Long, date: Date, dateInFormat: String)

    @Query(
        """select DISTINCT w.* from irregular_verb_quiz_item qi
         join irregular_verb_word w on w.id = qi.verb_id
        where qi.quiz_id = :quizId and qi.state = :state
    """
    )
    suspend fun findQuizWordsInState(
        quizId: Long,
        state: IrregularVerbQuizItemState
    ): List<IrregularVerbWordEntity>

    @Query("update irregular_verb_quiz set current_position = :number where id = :quizId")
    suspend fun updateQuizCurrentQuestion(quizId: Long, number: Int)

    @Query("select * from irregular_verb_quiz where id = :quizId limit 1")
    suspend fun findQuiz(quizId: Long): IrregularVerbQuizEntity?


}