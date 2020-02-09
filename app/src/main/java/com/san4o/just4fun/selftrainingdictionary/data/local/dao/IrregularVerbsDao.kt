package com.san4o.just4fun.selftrainingdictionary.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbWordEntity

@Dao
interface IrregularVerbsDao {

    @Query("select * from irregular_verb_word")
    suspend fun wordsList(): List<IrregularVerbWordEntity>

    @Query("select * from irregular_verb_word where id in (:ids)")
    suspend fun findVerbs(ids: List<Long>): List<IrregularVerbWordEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrSkip(entity: IrregularVerbWordEntity)

    @Query(
        """
        select * from irregular_verb_word 
            where present = :form1 
                 and past = :form2 
                 and perfect = :form3
            limit 1
         """
    )
    suspend fun find(form1: String, form2: String, form3: String): IrregularVerbWordEntity?

    @Query("delete from irregular_verb_word")
    suspend fun removeAll()
}