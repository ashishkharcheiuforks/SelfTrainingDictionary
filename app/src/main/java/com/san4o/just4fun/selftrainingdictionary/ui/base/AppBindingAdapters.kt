package com.san4o.just4fun.selftrainingdictionary.ui.base

import android.widget.TextView
import androidx.databinding.BindingAdapter

object AppBindingAdapters {

    @JvmStatic
    @BindingAdapter("app:bind_number")
    fun textViewSetNumber(view: TextView, number: Int) {
        view.text = number.toString()
    }
}