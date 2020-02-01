package com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quizwrite

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbItem
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbRepository
import kotlinx.coroutines.launch
import ru.sportmaster.android.driven.salespoint.presentation.core.single.LiveEvent
import ru.sportmaster.android.driven.salespoint.presentation.core.single.SingleLiveEvent

class IrregularVerbWriteQuizViewModel(
    private val repository: IrregularVerbRepository
) : ViewModel() {
    private var currentIndex = 0
    private var currentVerbItem: IrregularVerbItem? = null
    private var successCount = 0
    val current = ObservableInt()
    val allCount = ObservableInt()

    val questionWordForm1 = ObservableField<String>()
    val questionTranslateWordForm1 = ObservableField<String>()

    val answerPast = ObservableField<String>()
    val answerPerfect = ObservableField<String>()

    private val questions = ArrayList<IrregularVerbItem>()

    private val _state = MutableLiveData<IrregularVerbQuizState>()
    val state: LiveData<IrregularVerbQuizState> = _state
    private val _finishEvent = SingleLiveEvent<IrregularVerbResult>()
    val finishEvent: LiveEvent<IrregularVerbResult> = _finishEvent

    init {
        _state.value = IrregularVerbQuizState.Question

        viewModelScope.launch {
            questions.clear()
            questions.addAll(
                repository.wordsList()
                    .randomList(5)
            )

            allCount.set(questions.size)

            nextQuestion()
        }
    }

    fun onValidate() {
        val verb = currentVerbItem!!

        val answerPast = answerPast.get()
        val answerPerfect = answerPerfect.get()
        val last = currentIndex + 1 == questions.size
        if (isValid(verb, answerPast, answerPerfect)) {
            successCount++
            _state.value = IrregularVerbQuizState.Success(verb, last)
        } else {
            _state.value = IrregularVerbQuizState.Error(verb, last)
        }
    }

    private fun isValid(
        verb: IrregularVerbItem,
        answerPast: String?,
        answerPerfect: String?
    ): Boolean {
        return verb.past == answerPast && verb.perfect == answerPerfect
    }

    fun nextQuestion() {
        val index = currentIndex
        if (questions.size <= index) {
            _finishEvent.call(
                IrregularVerbResult(
                    right = successCount,
                    wrong = questions.size - successCount
                )
            )
        } else {
            answerPast.set("")
            answerPerfect.set("")

            _state.value = IrregularVerbQuizState.Question

            current.set(++currentIndex)

            currentVerbItem = questions[index]
            questionWordForm1.set(currentVerbItem!!.present)
            questionTranslateWordForm1.set(currentVerbItem!!.translation)


        }
    }
}

data class IrregularVerbResult(
    val right: Int,
    val wrong: Int
)


sealed class IrregularVerbQuizState {

    object Question : IrregularVerbQuizState()
    data class Success(val verb: IrregularVerbItem, val last: Boolean) : IrregularVerbQuizState()
    data class Error(val verb: IrregularVerbItem, val last: Boolean) : IrregularVerbQuizState()
}


private fun <E> List<E>.randomList(limit: Int): Collection<E> {
    val randomList = ArrayList<E>()

    while (randomList.size < limit) {
        val random = random()
        if (!randomList.contains(random)) {
            randomList.add(random)
        }
    }
    return randomList
}
