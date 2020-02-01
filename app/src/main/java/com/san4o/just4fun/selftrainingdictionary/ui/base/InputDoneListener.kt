package com.san4o.just4fun.selftrainingdictionary.ui.base

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView

class InputDoneListener(
    private val listener: () -> Unit
) : View.OnKeyListener,
    TextView.OnEditorActionListener {

    companion object {
        fun add(editText: EditText, listener: () -> Unit) {
            val inputDoneListener =
                InputDoneListener(
                    listener
                )
            editText.setOnKeyListener(inputDoneListener)
            editText.setOnEditorActionListener(inputDoneListener)
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            listener()
            return true
        }
        return false
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            listener()
            return true
        }
        return false
    }
}