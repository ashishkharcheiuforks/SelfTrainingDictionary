package com.san4o.just4fun.selftrainingdictionary.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.san4o.just4fun.selftrainingdictionary.R
import com.san4o.just4fun.selftrainingdictionary.databinding.FragmentIrregularVerbWriteQuizResultBinding
import com.san4o.just4fun.selftrainingdictionary.presentation.base.FunctionViewModelFactory
import com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quizwrite.IrregularVerbWriteQuizResultViewModel

class IrregularVerbWriteQuizResultFragment : Fragment() {

    companion object {
        const val ARG_RIGHT = "rightCount"
        const val ARG_WRONG = "wrongCount"

        fun arguments(right: Int, wrong: Int): Bundle {
            return bundleOf(
                ARG_RIGHT to right,
                ARG_WRONG to wrong
            )
        }
    }

    lateinit var binding: FragmentIrregularVerbWriteQuizResultBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentIrregularVerbWriteQuizResultBinding>(
            inflater,
            R.layout.fragment_irregular_verb_write_quiz_result,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rightCount = arguments?.getInt(ARG_RIGHT) ?: 1
        val wrongCount = arguments?.getInt(ARG_WRONG) ?: 2

        val viewModel by viewModels<IrregularVerbWriteQuizResultViewModel> {
            FunctionViewModelFactory {
                IrregularVerbWriteQuizResultViewModel(rightCount, wrongCount)
            }
        }

        binding.model = viewModel


    }
}