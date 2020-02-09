package com.san4o.just4fun.selftrainingdictionary.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.san4o.just4fun.selftrainingdictionary.R
import com.san4o.just4fun.selftrainingdictionary.databinding.FragmentIrregularVerbsQuizResultErrorsBinding
import com.san4o.just4fun.selftrainingdictionary.databinding.IrregularVerbResultItemBinding
import com.san4o.just4fun.selftrainingdictionary.di.lifecycle.AppScopeMember
import com.san4o.just4fun.selftrainingdictionary.di.viewmodel.SingleViewModelFactory
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerb
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbInteractor
import com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quizwrite.IrregularVerbQuizResultViewModel
import com.san4o.just4fun.selftrainingdictionary.ui.base.SimpleRecyclerViewListAdapter
import com.san4o.just4fun.selftrainingdictionary.ui.base.notifyError
import com.san4o.just4fun.selftrainingdictionary.ui.base.observeData
import com.san4o.just4fun.selftrainingdictionary.ui.base.observeVoidEvent
import javax.inject.Inject

class IrregularVerbQuizResultErrorFragment : Fragment(), AppScopeMember {

    companion object {
        private const val ARG_QUIZ_ID = "quiz.id"
        fun arguments(quizId: Long): Bundle {
            return bundleOf(
                ARG_QUIZ_ID to quizId
            )
        }
    }

    @Inject
    lateinit var interactor: IrregularVerbInteractor

    lateinit var binding: FragmentIrregularVerbsQuizResultErrorsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentIrregularVerbsQuizResultErrorsBinding>(
            inflater,
            R.layout.fragment_irregular_verbs_quiz_result_errors,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = SimpleRecyclerViewListAdapter<
                IrregularVerbResultItemBinding,
                IrregularVerb>(
            viewHolderLayoutRes = R.layout.irregular_verb_result_item,
            setModelFunction = { binding, item ->
                binding.text.text = "${item.present} - ${item.past} - ${item.perfect}"
            }
        )
        binding.verbsContainer.adapter = listAdapter
        binding.verbsContainer.layoutManager = LinearLayoutManager(requireContext())
        binding.okButton.setOnClickListener {
            findNavController().navigate(R.id.action_irregularVerbQuizResultErrorFragment_to_irrgularVerbsListFragment)
        }

        val quizId = arguments?.getLong(ARG_QUIZ_ID)
            ?: throw RuntimeException("Required param ${ARG_QUIZ_ID} is missing")
        val viewModel by viewModels<IrregularVerbQuizResultViewModel> {
            SingleViewModelFactory {
                IrregularVerbQuizResultViewModel(interactor, quizId)
            }
        }

        viewModel.errorEvent.observeVoidEvent(this) {
            notifyError()
        }
        viewModel.errorVerbs.observeData(this) {
            listAdapter.refreshItems(it)
        }
    }


}
