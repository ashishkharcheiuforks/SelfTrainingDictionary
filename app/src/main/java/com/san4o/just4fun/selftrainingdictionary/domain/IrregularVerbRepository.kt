package com.san4o.just4fun.selftrainingdictionary.domain

import kotlinx.coroutines.flow.Flow

interface IrregularVerbRepository {

    fun wordsList(): Flow<List<IrregularVerbItem>>
}