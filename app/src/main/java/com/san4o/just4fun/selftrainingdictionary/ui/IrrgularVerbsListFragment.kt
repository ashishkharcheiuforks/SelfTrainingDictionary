package com.san4o.just4fun.selftrainingdictionary.ui


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.san4o.just4fun.selftrainingdictionary.R
import com.san4o.just4fun.selftrainingdictionary.databinding.FragmentIrrgularVerbsListBinding
import com.san4o.just4fun.selftrainingdictionary.databinding.IrregularVerbListItemBinding
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbItem
import com.san4o.just4fun.selftrainingdictionary.presentation.irregular.list.IrregualrVersListViewModel
import com.san4o.just4fun.selftrainingdictionary.ui.base.DataBindingViewHolder
import com.san4o.just4fun.selftrainingdictionary.ui.base.RecyclerViewListAdapter
import com.san4o.just4fun.selftrainingdictionary.ui.base.observeData
import javax.inject.Inject

class IrrgularVerbsListFragment : Fragment() {

    @Inject
    lateinit var viewModel: IrregualrVersListViewModel

    lateinit var binding: FragmentIrrgularVerbsListBinding

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

        viewModel.items.observeData(this) {
                adapter.refreshItems(it)
            }

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_irrgularVerbsListFragment_to_irregularVerbsTestFragment)
        }
    }


    class RecyclerViewAdapter :
        RecyclerViewListAdapter<RecyclerViewAdapter.AdapterViewHolder, IrregularVerbItem>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
            return create(parent.context, parent)
        }

        override fun setItem(holder: AdapterViewHolder, item: IrregularVerbItem) {
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
            fun setModel(item: IrregularVerbItem) {
                binding.text.text = "${item.present} - ${item.past} - ${item.perfect}"
            }

        }
    }
}



