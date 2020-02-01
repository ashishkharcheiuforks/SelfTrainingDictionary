package com.san4o.just4fun.selftrainingdictionary.ui.base

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeData(owner:LifecycleOwner, func:(T) -> Unit){
    this.observe(owner, Observer { func.invoke(it) })
}

fun View.showKeyboard() {
    val view = this@showKeyboard
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .run {
            showSoftInput(view, 0)
        }
}

fun View.hideKeyboard() {
    val view = this
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .run {
            hideSoftInputFromWindow(view.windowToken, 0)
        }
}

fun Context.showKeyboardFrom(view: View) {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .run {
            showSoftInput(view, 0)
        }
}

fun Context.hideKeyboardFrom(view: View) {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .run {
            hideSoftInputFromWindow(view.windowToken, 0)
        }
}
