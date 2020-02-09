package com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quizwrite

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerb
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbInteractor
import kotlinx.coroutines.launch
import ru.sportmaster.android.driven.salespoint.domain.base.failure.Failure
import ru.sportmaster.android.driven.salespoint.presentation.core.single.LiveEvent
import ru.sportmaster.android.driven.salespoint.presentation.core.single.SingleLiveEvent
import ru.sportmaster.android.driven.salespoint.presentation.core.single.SingleLiveVoidEvent
import timber.log.Timber

class IrregularVerbQuizViewModel(
    private val interactor: IrregularVerbInteractor,
    private val quizId: Long
) : ViewModel() {

    val currentCount = ObservableInt()
    val totalCount = ObservableInt()

    val questionWordForm1 = ObservableField<String>()
    val questionTranslateWordForm1 = ObservableField<String>()

    val answerPast = ObservableField<String>()
    val answerPerfect = ObservableField<String>()

    private val questions = ArrayList<IrregularVerb>()
    private val answer = MutableLiveData<Answer>()
    private var currentIndex = 0
    private lateinit var current: IrregularVerb


    private val errorAnswers = ArrayList<ErrorAnswer>()
    private var finishAnswer = false

    private val question = SingleLiveVoidEvent()
    private val finish = SingleLiveEvent<CompleteParams>()
    val errorEvent = SingleLiveVoidEvent()

    private var successes = 0
    private var errors = 0

    init {
        viewModelScope.launch {
            interactor.findQuiz(quizId)
                .handle(
                    onSuccess = {
                        questions.addAll(it.verbs)
                        totalCount.set(it.verbs.size)

                        currentIndex = it.startPosition
                        errors = it.errors
                        successes = it.successes
                        setQuestion(false)
                    },
                    onFailure = ::onFailure
                )
        }
    }

    private fun onFailure(f: Failure) {
        errorEvent.call()
    }

    fun answer(): LiveData<Answer> = answer
    fun finish(): LiveEvent<CompleteParams> = finish
    fun question(): LiveEvent<Void?> = question
    fun error(): LiveEvent<Void?> = errorEvent

    fun onValidate() {
        val pastAnswer = answerPast.get() ?: ""
        val perfectAnswer = answerPerfect.get() ?: ""
        val verbItem = current

        val valid = verbItem.isEquals(pastAnswer, perfectAnswer)
        answer.value = Answer(
            verb = verbItem,
            past = pastAnswer,
            perfect = perfectAnswer,
            valid = valid,
            last = questions.indexOf(verbItem) == questions.lastIndex
        )
        if (!valid) {
            if (errorAnswers.none { it.verb.id == verbItem.id }) {
                errorAnswers.add(
                    ErrorAnswer(
                        verb = verbItem,
                        past = pastAnswer,
                        perfect = perfectAnswer
                    )
                )
            }


        }
        if (!finishAnswer) {
            answerResult(verbItem.id, valid)
        }
    }

    private fun answerResult(verbId: Long, success: Boolean) = viewModelScope.launch {
        Timber.d("answerResult(id=$verbId, success=$success)")
        interactor.answerResult(quizId, verbId, success)
    }


    fun onNextQuestion() = viewModelScope.launch {
        if (questions.lastIndex <= currentIndex) {
            if (!finishAnswer) {
                errors += errorAnswers.size
                successes += questions.size - errorAnswers.size
                interactor.finishQuiz(quizId)
            }
            finishAnswer = true

            if (errorAnswers.isNotEmpty()) {
                setNextErrorQuestion()
            } else {
                finish.call(CompleteParams(quizId, errors))
            }
        } else {
            setQuestion()
        }
    }

    private fun setQuestion(next: Boolean = true) = viewModelScope.launch {
        val index = if (next) {
            ++currentIndex
        } else {
            currentIndex
        }

        if (next) {
            interactor.updateQuizCurrentQuestion(quizId, index)
        }
        setQuestion(index)


    }

    private fun setQuestion(index: Int) {
        val verbItem = questions[index]
        current = verbItem
        currentCount.set(index + 1)

        setQuestionVerbView(verbItem)

        question.call()
    }

    private fun setNextErrorQuestion() {
        val random = errorAnswers.random()
        current = random.verb

        setQuestionVerbView(random.verb)
        errorAnswers.remove(random)

        question.call()
    }

    private fun setQuestionVerbView(verbItem: IrregularVerb) {
        questionWordForm1.set(verbItem.present)
        questionTranslateWordForm1.set(verbItem.translation)
        answerPast.set("")
        answerPerfect.set("")
    }


    data class Answer(
        val verb: IrregularVerb,
        val past: String,
        val perfect: String,
        val valid: Boolean,
        val last: Boolean
    )

    data class ErrorAnswer(
        val verb: IrregularVerb,
        val past: String,
        val perfect: String
    )

    data class CompleteParams(
        val quizId: Long,
        val errors: Int
    ) {

    }

}


private fun IrregularVerb.isEquals(past: String, perfect: String): Boolean =
    this.past == past && this.perfect == perfect