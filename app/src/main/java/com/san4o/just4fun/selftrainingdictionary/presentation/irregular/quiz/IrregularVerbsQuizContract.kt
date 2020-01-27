package com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quiz

import androidx.databinding.ObservableField

interface IrregularVerbsQuizContract {
    interface Interactor{
        fun onAnswer1()
        fun onAnswer2()
        fun onAnswer3()
        fun onAnswer4()
    }

    interface View{
        val question : ObservableField<String>
        val answer1 : ObservableField<String>
        val answer2 : ObservableField<String>
        val answer3 : ObservableField<String>
        val answer4 : ObservableField<String>
    }
}