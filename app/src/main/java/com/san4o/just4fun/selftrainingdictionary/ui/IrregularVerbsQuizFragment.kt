package com.san4o.just4fun.selftrainingdictionary.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.san4o.just4fun.selftrainingdictionary.R

class IrregularVerbsQuizFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_irregular_verb_write_quiz, container, false)
    }


}