package com.san4o.just4fun.selftrainingdictionary.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbWordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IrregularVerbsDao {
    @Query("select * from irregular_verb_word ")
    fun wordsList(): Flow<List<IrregularVerbWordEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrSkip(entity: IrregularVerbWordEntity)

    @Query("delete from irregular_verb_word")
    suspend fun removeAll()
}