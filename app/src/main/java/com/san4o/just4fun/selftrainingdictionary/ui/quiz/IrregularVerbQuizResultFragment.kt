package com.san4o.just4fun.selftrainingdictionary.ui.quiz


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.san4o.just4fun.selftrainingdictionary.R
import com.san4o.just4fun.selftrainingdictionary.databinding.FragmentIrregularVerbsQuizResultBinding

class IrregularVerbQuizResultFragment : Fragment() {

    lateinit var binding: FragmentIrregularVerbsQuizResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentIrregularVerbsQuizResultBinding>(
            inflater,
            R.layout.fragment_irregular_verbs_quiz_result,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.okButton.setOnClickListener {
            findNavController().navigate(R.id.action_irregularVerbQuizResultFragment_to_irrgularVerbsListFragment)
        }
    }
}
