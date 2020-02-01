package com.san4o.just4fun.selftrainingdictionary.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FunctionViewModelFactory(
    private val viewModelCreateFunc: () -> ViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModelCreateFunc.invoke() as T
    }
}