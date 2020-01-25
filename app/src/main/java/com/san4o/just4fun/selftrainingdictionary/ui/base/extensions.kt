package com.san4o.just4fun.selftrainingdictionary.ui.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeData(owner:LifecycleOwner, func:(T) -> Unit){
    this.observe(owner, Observer { func.invoke(it) })
}