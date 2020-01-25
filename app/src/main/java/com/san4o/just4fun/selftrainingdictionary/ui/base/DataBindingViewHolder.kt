package com.san4o.just4fun.selftrainingdictionary.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class DataBindingViewHolder<T : ViewDataBinding>(protected open val binding: T) :
    RecyclerView.ViewHolder(binding.root) {
}