package com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quiz

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbItem
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbRepository
import kotlinx.coroutines.launch

class IrregularVerbsQuizViewModel(
    private val repository: IrregularVerbRepository
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

            val wordsList = repository.wordsList()
            questions.addAll(
                wordsList.map { createQuestion(wordsList, it) }
            )

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
        words: List<IrregularVerbItem>,
        item: IrregularVerbItem
    ): IrregularVerbsQuizQuestion {
        val present = item.present

        val random = UniqueQuestionFromList(words.map { it.present }, present)

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