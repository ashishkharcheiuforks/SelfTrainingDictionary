package com.san4o.just4fun.selftrainingdictionary.presentation.irregular.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbListItem
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVersListInteractor
import kotlinx.coroutines.launch
import ru.sportmaster.android.driven.salespoint.domain.base.failure.Failure
import ru.sportmaster.android.driven.salespoint.presentation.core.single.SingleLiveEvent
import ru.sportmaster.android.driven.salespoint.presentation.core.single.SingleLiveVoidEvent
import java.util.*
import javax.inject.Inject

class IrregularVersListViewModel @Inject constructor(
    private val interactor: IrregularVersListInteractor
) : ViewModel() {
    val items = MutableLiveData<List<IrregularVerbListItem>>()

    val currentQuiz = MutableLiveData<Current>()
    val startQuiz = SingleLiveEvent<StartQuizParams>()
    val notifyErrorEvent = SingleLiveVoidEvent()

    init {

    }

    fun refreshState() {
        viewModelScope.launch {
            currentQuiz.value = Current.None

            interactor.wordsList()
                .handle(
                    onSuccess = { list -> items.value = list.sortedWith(VerbResultComparator()) },
                    onFailure = this@IrregularVersListViewModel::onFailure
                )

            interactor.currentQuiz()
                .handle(
                    onSuccess = {
                        currentQuiz.value =
                            Current.Quiz(
                                id = it.id,
                                date = it.date,
                                current = it.current,
                                total = it.total
                            )
                    },
                    onFailure = this@IrregularVersListViewModel::onFailure
                )
        }
    }


    fun onStartQuiz() {
        val current = currentQuiz.value
        when (current) {
            is Current.None -> {
                viewModelScope.launch {

                    interactor.createQuiz()
                        .handle(
                            onSuccess = { id -> startQuiz(id) },
                            onFailure = this@IrregularVersListViewModel::onFailure
                        )
                }

            }
            is Current.Quiz -> {
                startQuiz(current.id)
            }
        }
    }

    private fun startQuiz(id: Long) {
        startQuiz.call(
            StartQuizParams(id)
        )
    }

    private fun onFailure(f: Failure) {
        if (f !is Failure.NotFound) {
            notifyErrorEvent.call()
        }
    }


}

data class StartQuizParams(
    val id: Long
)

sealed class Current {
    data class Quiz(
        val id: Long,
        val date: Date,
        val current: Int,
        val total: Int
    ) : Current()

    object None : Current()
}

class VerbResultComparator : Comparator<IrregularVerbListItem> {
    override fun compare(
        o1: IrregularVerbListItem,
        o2: IrregularVerbListItem
    ): Int {
        val compareTo = -1 * o1.wrong.compareTo(o2.wrong)
        if (compareTo != 0) {
            return compareTo
        }
        val correctCompare = -1 * o1.correct.compareTo(o2.correct)
        if (correctCompare != 0) {
            return correctCompare
        }
        return o1.verb.present.compareTo(o2.verb.present)
    }
}


