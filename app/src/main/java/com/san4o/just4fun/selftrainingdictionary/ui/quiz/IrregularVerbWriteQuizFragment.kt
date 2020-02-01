package com.san4o.just4fun.selftrainingdictionary.ui.quiz


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.san4o.just4fun.selftrainingdictionary.R
import com.san4o.just4fun.selftrainingdictionary.databinding.FragmentIrregularVerbWriteQuizBinding
import com.san4o.just4fun.selftrainingdictionary.di.lifecycle.AppScopeMember
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbItem
import com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quizwrite.IrregularVerbQuizState
import com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quizwrite.IrregularVerbWriteQuizViewModel
import com.san4o.just4fun.selftrainingdictionary.ui.base.InputDoneListener
import com.san4o.just4fun.selftrainingdictionary.ui.base.hideKeyboard
import com.san4o.just4fun.selftrainingdictionary.ui.base.showKeyboard
import javax.inject.Inject


class IrregularVerbWriteQuizFragment : Fragment(), AppScopeMember {

    lateinit var binding: FragmentIrregularVerbWriteQuizBinding

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    var successAnswerSheet: BottomSheetDialogFragment? = null
    var errorAnswerSheet: BottomSheetDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_irregular_verb_write_quiz,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel by viewModels<IrregularVerbWriteQuizViewModel> {
            factory
        }
        binding.model = viewModel

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is IrregularVerbQuizState.Question -> {
                    binding.form2EditText.requestFocus()
                    binding.form2EditText.showKeyboard()

                    successAnswerSheet?.dismiss()
                    errorAnswerSheet?.dismiss()
                }
                is IrregularVerbQuizState.Success -> {

                    successAnswerSheet = showSheet(
                        viewModel,
                        IrregularVerbQuizAnswerSheet.Mode.SUCCESS,
                        state.last,
                        state.verb
                    )
                }
                is IrregularVerbQuizState.Error -> {

                    errorAnswerSheet = showSheet(
                        viewModel,
                        IrregularVerbQuizAnswerSheet.Mode.ERROR,
                        state.last,
                        state.verb
                    )
                }
            }
        })
        viewModel.finishEvent.observe(this, Observer {
            errorAnswerSheet?.dismiss()
            successAnswerSheet?.dismiss()

            findNavController().navigate(
                R.id.action_irregularVerbWriteQuizFragment_to_irregularVerbWriteQuizResultFragment,
                IrregularVerbWriteQuizResultFragment.arguments(it.right, it.wrong)
            )
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
        viewModel: IrregularVerbWriteQuizViewModel,
        mode: IrregularVerbQuizAnswerSheet.Mode,
        last: Boolean,
        verbItem: IrregularVerbItem
    ): BottomSheetDialogFragment {
        return IrregularVerbQuizAnswerSheet.show(
            fragmentManager = parentFragmentManager,
            mode = mode,
            last = last,
            verb = verbItem,
            actionClickListener = { viewModel.nextQuestion() }
        )
    }


}

