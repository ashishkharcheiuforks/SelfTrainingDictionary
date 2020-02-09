package com.san4o.just4fun.selftrainingdictionary.ui.quiz


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.san4o.just4fun.selftrainingdictionary.R
import com.san4o.just4fun.selftrainingdictionary.databinding.FragmentIrregularVerbsQuizBinding
import com.san4o.just4fun.selftrainingdictionary.di.lifecycle.AppScopeMember
import com.san4o.just4fun.selftrainingdictionary.di.viewmodel.SingleViewModelFactory
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerb
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbInteractor
import com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quizwrite.IrregularVerbQuizViewModel
import com.san4o.just4fun.selftrainingdictionary.ui.base.InputDoneListener
import com.san4o.just4fun.selftrainingdictionary.ui.base.hideKeyboard
import com.san4o.just4fun.selftrainingdictionary.ui.base.notifyError
import com.san4o.just4fun.selftrainingdictionary.ui.base.showKeyboard
import javax.inject.Inject


class IrregularVerbQuizFragment : Fragment(), AppScopeMember {

    companion object {
        private const val ARG_QUIZ_ID = "quiz.id"
        fun arguments(id: Long): Bundle = bundleOf(ARG_QUIZ_ID to id)
    }

    lateinit var binding: FragmentIrregularVerbsQuizBinding

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    @Inject
    lateinit var interactor: IrregularVerbInteractor

    var successAnswerSheet: BottomSheetDialogFragment? = null
    var errorAnswerSheet: BottomSheetDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_irregular_verbs_quiz,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val quizId = arguments?.getLong(ARG_QUIZ_ID)
            ?: throw RuntimeException("Required param $ARG_QUIZ_ID is missing")
        val viewModel by viewModels<IrregularVerbQuizViewModel> {
            SingleViewModelFactory {
                IrregularVerbQuizViewModel(interactor, quizId)
            }
        }
        binding.model = viewModel

        viewModel.answer().observe(this, Observer { answer ->
            if (answer.valid) {
                successAnswerSheet = showSheet(
                    IrregularVerbQuizAnswerSheet.Mode.SUCCESS,
                    answer.last,
                    answer.verb
                ) { viewModel.onNextQuestion() }
            } else {
                successAnswerSheet = showSheet(
                    IrregularVerbQuizAnswerSheet.Mode.ERROR,
                    answer.last,
                    answer.verb
                ) { viewModel.onNextQuestion() }
            }

        })
        viewModel.finish().observe(this, Observer {
            errorAnswerSheet?.dismiss()
            successAnswerSheet?.dismiss()

            if (it.errors == 0) {
                findNavController().navigate(
                    R.id.action_writeAnswersIrregularVerbQuizFragment_to_irregularVerbQuizResultFragment
                )
            } else {
                findNavController().navigate(
                    R.id.action_writeAnswersIrregularVerbQuizFragment_to_irregularVerbQuizResultErrorFragment,
                    IrregularVerbQuizResultErrorFragment.arguments(it.quizId)
                )
            }


        })
        viewModel.error().observe(this, Observer {
            notifyError()
        })
        viewModel.question().observe(this, Observer {
            successAnswerSheet?.dismiss()
            errorAnswerSheet?.dismiss()

            binding.form2EditText.requestFocus()
            binding.form2EditText.showKeyboard()
        })

        InputDoneListener.add(binding.form2EditText) {
            binding.form3EditText.requestFocus()
            binding.form3EditText.showKeyboard()
        }
        InputDoneListener.add(binding.form3EditText) {
            binding.form3EditText.clearFocus()
            binding.form3EditText.hideKeyboard()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        errorAnswerSheet?.dismiss()
        successAnswerSheet?.dismiss()
    }

    private fun showSheet(
        mode: IrregularVerbQuizAnswerSheet.Mode,
        last: Boolean,
        verbItem: IrregularVerb,
        function: () -> Unit
    ): BottomSheetDialogFragment {
        return IrregularVerbQuizAnswerSheet.show(
            fragmentManager = parentFragmentManager,
            mode = mode,
            last = last,
            verb = verbItem,
            actionClickListener = function
        )
    }


}

