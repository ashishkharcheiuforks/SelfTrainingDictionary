package com.san4o.just4fun.selftrainingdictionary.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.san4o.just4fun.selftrainingdictionary.R

/**
 * A simple [Fragment] subclass.
 */
class WordsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_words, container, false)
    }


}
