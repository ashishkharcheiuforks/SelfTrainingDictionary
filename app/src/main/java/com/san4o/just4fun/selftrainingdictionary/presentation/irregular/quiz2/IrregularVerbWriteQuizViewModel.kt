package com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quiz2

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbRepository
import kotlinx.coroutines.launch

class IrregularVerbWriteQuizViewModel(
    private val repository: IrregularVerbRepository
) : ViewModel() {
    val current = ObservableInt()
    val allCount = ObservableInt()

    val questionWordForm1 = ObservableField<String>()
    val questionTranslateWordForm1 = ObservableField<String>()

    val answerForm1 = ObservableField<String>()
    val answerForm2 = ObservableField<String>()

    init {
        viewModelScope.launch {
            val list = repository.wordsList()


        }

    }
}