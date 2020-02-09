package com.san4o.just4fun.selftrainingdictionary.di.viewmodel

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SingleViewModelFactory<VM : ViewModel>(
    private val createFunc: () -> VM
) : ViewModelProvider.Factory {

    @NonNull
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        return createFunc() as T
    }

}