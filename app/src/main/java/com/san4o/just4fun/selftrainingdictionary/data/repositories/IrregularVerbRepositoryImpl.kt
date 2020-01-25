package com.san4o.just4fun.selftrainingdictionary.data.repositories

import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsDao
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbItem
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IrregularVerbRepositoryImpl(
    private val dao: IrregularVerbsDao
) : IrregularVerbRepository {

    override fun wordsList(): Flow<List<IrregularVerbItem>> {
        return dao.wordsList()
            .map { list ->
                list.map {
                    IrregularVerbItem(
                        id = it.id,
                        present = it.present,
                        past = it.past,
                        perfect = it.perfect
                    )
                }
                    .sortedBy {
                        it.present
                    }
            }
    }
}