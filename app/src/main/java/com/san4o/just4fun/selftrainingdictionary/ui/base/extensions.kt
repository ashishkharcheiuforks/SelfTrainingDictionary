package com.san4o.just4fun.selftrainingdictionary.ui.base

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import ru.sportmaster.android.driven.salespoint.presentation.core.single.SingleLiveVoidEvent

fun <T> LiveData<T>.observeData(owner:LifecycleOwner, func:(T) -> Unit){
    this.observe(owner, Observer { func.invoke(it) })
}

fun <T> LiveData<T?>.observeNulableData(owner: LifecycleOwner, func: (T?) -> Unit) {
    this.observe(owner, Observer { func.invoke(it) })
}

fun SingleLiveVoidEvent.observeVoidEvent(owner: LifecycleOwner, func: () -> Unit) {
    this.observe(owner, Observer { func.invoke() })
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

fun Fragment.notifyError() {
    Toast.makeText(requireContext(), "Ошибка выполнения", Toast.LENGTH_LONG)
        .show()
}

