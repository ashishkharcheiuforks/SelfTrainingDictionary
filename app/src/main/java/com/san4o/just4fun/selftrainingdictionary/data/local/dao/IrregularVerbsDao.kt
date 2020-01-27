package com.san4o.just4fun.selftrainingdictionary.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbWordEntity

@Dao
interface IrregularVerbsDao {
    @Query("select * from irregular_verb_word ")
    fun wordsLiveData(): LiveData<List<IrregularVerbWordEntity>>

    @Query("select * from irregular_verb_word ")
    suspend fun wordsList(): List<IrregularVerbWordEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrSkip(entity: IrregularVerbWordEntity)

    @Query("delete from irregular_verb_word")
    suspend fun removeAll()


}