package com.san4o.just4fun.selftrainingdictionary.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsDao
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbWordEntity
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbItem
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbRepository

class IrregularVerbRepositoryImpl(
    private val dao: IrregularVerbsDao
) : IrregularVerbRepository {

    override fun wordsLiveData(): LiveData<List<IrregularVerbItem>> {
        return Transformations.map(dao.wordsLiveData())
        { list ->
            list.map {
                convert(it)
            }
                .sortedBy {
                    it.present
                }
        }
    }

    private fun convert(entity: IrregularVerbWordEntity): IrregularVerbItem {
        return IrregularVerbItem(
            id = entity.id,
            translation = entity.translation,
            present = entity.present,
            past = entity.past,
            perfect = entity.perfect
        )
    }

    override suspend fun wordsList(): List<IrregularVerbItem> {
        return dao.wordsList()
            .map { convert(it) }
    }
}