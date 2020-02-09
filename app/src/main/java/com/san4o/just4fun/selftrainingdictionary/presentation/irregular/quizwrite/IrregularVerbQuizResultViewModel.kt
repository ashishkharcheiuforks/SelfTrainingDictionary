package com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quizwrite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerb
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbInteractor
import kotlinx.coroutines.launch
import ru.sportmaster.android.driven.salespoint.domain.base.failure.Failure
import ru.sportmaster.android.driven.salespoint.presentation.core.single.SingleLiveVoidEvent

class IrregularVerbQuizResultViewModel(
    private val interactor: IrregularVerbInteractor,
    private val quizId: Long
) : ViewModel() {

    val errorVerbs = MutableLiveData<List<IrregularVerb>>()
    val errorEvent = SingleLiveVoidEvent()

    init {
        viewModelScope.launch {
            interactor.findQuizErrorWords(quizId)
                .handle(
                    onSuccess = {
                        errorVerbs.value = it
                    },
                    onFailure = this@IrregularVerbQuizResultViewModel::onFailure
                )
        }
    }

    private fun onFailure(f: Failure) {
        errorEvent.call()
    }
}