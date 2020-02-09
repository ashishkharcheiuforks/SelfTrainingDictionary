package com.san4o.just4fun.selftrainingdictionary.ui


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.san4o.just4fun.selftrainingdictionary.R
import com.san4o.just4fun.selftrainingdictionary.databinding.FragmentIrrgularVerbsListBinding
import com.san4o.just4fun.selftrainingdictionary.databinding.IrregularVerbListItemBinding
import com.san4o.just4fun.selftrainingdictionary.di.lifecycle.AppScopeMember
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbListItem
import com.san4o.just4fun.selftrainingdictionary.presentation.irregular.list.Current
import com.san4o.just4fun.selftrainingdictionary.presentation.irregular.list.IrregularVersListViewModel
import com.san4o.just4fun.selftrainingdictionary.ui.base.*
import com.san4o.just4fun.selftrainingdictionary.ui.quiz.IrregularVerbQuizFragment
import javax.inject.Inject

class IrregularVerbsListFragment : Fragment(), AppScopeMember {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var binding: FragmentIrrgularVerbsListBinding

    lateinit var viewModel: IrregularVersListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_irrgular_verbs_list,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.list
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        val adapter = RecyclerViewAdapter()
        recyclerView.adapter = adapter

        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val viewModel by viewModels<IrregularVersListViewModel> { factory }
        this.viewModel = viewModel
        viewModel.items.observeData(this) {
            adapter.refreshItems(it)
        }

        binding.addButton.setOnClickListener {
            viewModel.onStartQuiz()
        }

        viewModel.notifyErrorEvent.observeVoidEvent(this) {
            notifyError()
        }
        viewModel.currentQuiz.observeData(this) {
            when (it) {
                is Current.None -> {
                    binding.addButton.text = requireContext().getString(R.string.start_test)
                }
                is Current.Quiz -> {
                    binding.addButton.text = requireContext()
                        .getString(R.string.continue_test, it.current, it.total)
                }
            }
        }
        viewModel.startQuiz.observeData(this) {
            findNavController()
                .navigate(
                    R.id.action_irrgularVerbsListFragment_to_writeAnswersIrregularVerbQuizFragment,
                    IrregularVerbQuizFragment.arguments(it.id)
                )
        }

    }

    override fun onStart() {
        super.onStart()

        viewModel.refreshState()
    }


    class RecyclerViewAdapter :
        RecyclerViewListAdapter<RecyclerViewAdapter.AdapterViewHolder, IrregularVerbListItem>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
            return create(parent.context, parent)
        }

        override fun setItem(holder: AdapterViewHolder, item: IrregularVerbListItem) {
            holder.setModel(item)
        }

        private fun create(context: Context, parent: ViewGroup): AdapterViewHolder {
            val binding: IrregularVerbListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.irregular_verb_list_item,
                parent,
                false
            )
            return AdapterViewHolder(binding)
        }

        class AdapterViewHolder(
            binding: IrregularVerbListItemBinding
        ) : DataBindingViewHolder<IrregularVerbListItemBinding>(binding) {
            fun setModel(item: IrregularVerbListItem) {
                binding.text.text =
                    "${item.verb.present} - ${item.verb.past} - ${item.verb.perfect}"
                binding.wrong.text = item.wrong.toString()
                binding.correct.text = item.correct.toString()
            }

        }
    }
}



