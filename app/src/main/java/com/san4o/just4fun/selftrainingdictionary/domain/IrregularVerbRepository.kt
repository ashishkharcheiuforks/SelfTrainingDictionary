package com.san4o.just4fun.selftrainingdictionary.domain

import androidx.lifecycle.LiveData

interface IrregularVerbRepository {

    suspend fun wordsList(): List<IrregularVerbItem>

    fun wordsLiveData(): LiveData<List<IrregularVerbItem>>
}