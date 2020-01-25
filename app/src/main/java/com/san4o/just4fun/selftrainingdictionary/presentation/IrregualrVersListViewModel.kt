package com.san4o.just4fun.selftrainingdictionary.presentation

import androidx.lifecycle.ViewModel
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbItem
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbRepository
import kotlinx.coroutines.flow.Flow

class IrregualrVersListViewModel(
    private val repository: IrregularVerbRepository
) : ViewModel() {
    val items: Flow<List<IrregularVerbItem>> = repository.wordsList()

    init {

    }
}