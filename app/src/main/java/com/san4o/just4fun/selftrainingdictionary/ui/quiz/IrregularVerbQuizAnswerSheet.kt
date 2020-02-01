package com.san4o.just4fun.selftrainingdictionary.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.san4o.just4fun.selftrainingdictionary.R
import com.san4o.just4fun.selftrainingdictionary.databinding.QuizSuccessAnswerBinding
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbItem

class IrregularVerbQuizAnswerSheet : BottomSheetDialogFragment() {

    enum class Mode {
        SUCCESS,
        ERROR

    }

    companion object {
        val TAG = IrregularVerbQuizAnswerSheet::class.java.simpleName

        fun show(
            fragmentManager: FragmentManager,
            mode: Mode,
            last: Boolean,
            verb: IrregularVerbItem,
            actionClickListener: () -> Unit
        ): BottomSheetDialogFragment {

            val fragment =
                IrregularVerbQuizAnswerSheet()
            fragment.mode = mode
            fragment.verb = verb
            fragment.last = last
            fragment.actionClickListener = View.OnClickListener { actionClickListener.invoke() }
            fragment.show(
                fragmentManager,
                TAG
            )
            return fragment
        }
    }

    var mode: Mode =
        Mode.SUCCESS
    var verb: IrregularVerbItem = IrregularVerbItem(0, "", "", "", "")
    var last: Boolean = false
    var actionClickListener: View.OnClickListener = View.OnClickListener { }

    lateinit var binding: QuizSuccessAnswerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<QuizSuccessAnswerBinding>(
            inflater,
            R.layout.quiz_success_answer,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (mode) {
            Mode.SUCCESS -> {
                binding.answerIcon.setImageDrawable(requireContext().getDrawable(R.drawable.ic_success_48))
                binding.rightAnswer.setTextColor(requireContext().getColor(android.R.color.black))
            }
            Mode.ERROR -> {
                binding.answerIcon.setImageDrawable(requireContext().getDrawable(R.drawable.ic_error_48))
                binding.rightAnswer.setTextColor(requireContext().getColor(R.color.primaryError))
            }
        }
        binding.rightAnswer.text = getString(
            R.string.irregular_verb_full_text_format,
            verb.present,
            verb.past,
            verb.perfect
        )
        binding.translation.text = getString(R.string.in_brackets_text_format, verb.translation)
        binding.okButton.setOnClickListener(actionClickListener)
        binding.okButton.text = if (last) {
            getString(R.string.end)
        } else {
            getString(R.string.next)
        }
    }
}