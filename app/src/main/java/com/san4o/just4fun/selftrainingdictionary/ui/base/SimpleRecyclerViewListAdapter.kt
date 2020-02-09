package com.san4o.just4fun.selftrainingdictionary.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class SimpleRecyclerViewListAdapter<Binding : ViewDataBinding, Item>(
    @LayoutRes
    private val viewHolderLayoutRes: Int,
    private val setModelFunction: (Binding, Item) -> Unit
) :
    RecyclerViewListAdapter<SimpleRecyclerViewListAdapter.AdapterViewHolder<Binding, Item>, Item>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterViewHolder<Binding, Item> {
        return create(parent.context, parent)
    }

    override fun setItem(holder: AdapterViewHolder<Binding, Item>, item: Item) {
        holder.setModel(item)
    }

    private fun create(context: Context, parent: ViewGroup): AdapterViewHolder<Binding, Item> {
        val binding: Binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            viewHolderLayoutRes,
            parent,
            false
        )
        return AdapterViewHolder(binding, setModelFunction)
    }

    class AdapterViewHolder<Binding : ViewDataBinding, Item>(
        binding: Binding,
        private val setModelFunction: (Binding, Item) -> Unit
    ) : DataBindingViewHolder<Binding>(binding) {
        fun setModel(item: Item) {
            setModelFunction.invoke(binding, item)
        }

    }
}