package com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quiz

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbListItem

import kotlinx.coroutines.launch

class IrregularVerbsQuizViewModel(

) : ViewModel(), IrregularVerbsQuizContract.Interactor {

    private val _state = IrregularVerbsQuizViewState()
    val state: IrregularVerbsQuizContract.View = _state

    private val questions = ArrayList<IrregularVerbsQuizQuestion>()
    private val answers = ArrayList<IrregularVerbAnswer>()
    private lateinit var currentQuestion: IrregularVerbsQuizQuestion
    private var currentIndex = 0

    init {
        viewModelScope.launch {
            questions.clear()


            currentQuestion = questions[currentIndex]
        }
    }


    override fun onAnswer1() {

    }

    override fun onAnswer2() {
    }

    override fun onAnswer3() {
    }

    override fun onAnswer4() {
    }

    private fun createQuestion(
        words: List<IrregularVerbListItem>,
        item: IrregularVerbListItem
    ): IrregularVerbsQuizQuestion {
        val present = item.verb.present

        val random = UniqueQuestionFromList(words.map { it.verb.present }, present)

        return IrregularVerbsQuizQuestion(
            valid = present,

            answer1 = random.next(),
            answer2 = random.next(),
            answer3 = random.next(),
            answer4 = random.next()
        )
    }

}

class IrregularVerbsQuizViewState : IrregularVerbsQuizContract.View {
    override val question = ObservableField<String>()
    override val answer1 = ObservableField<String>()
    override val answer2 = ObservableField<String>()
    override val answer3 = ObservableField<String>()
    override val answer4 = ObservableField<String>()

}