package com.primarynet.SmartBus.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 2/28/19.
 */
object KeyboardUtil {

    fun showKeyboard(context: Context, v: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (v.requestFocus()) {
            imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideKeyboard(context: Context, v: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }
}// no instance
