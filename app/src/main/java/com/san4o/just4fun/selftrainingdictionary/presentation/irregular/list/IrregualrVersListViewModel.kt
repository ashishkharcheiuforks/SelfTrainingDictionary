package com.san4o.just4fun.selftrainingdictionary.presentation.irregular.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbItem
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbRepository

class IrregualrVersListViewModel(
    private val repository: IrregularVerbRepository
) : ViewModel() {
    val items: LiveData<List<IrregularVerbItem>> = repository.wordsLiveData()

    init {

    }
}