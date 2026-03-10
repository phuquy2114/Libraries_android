package com.uits.baseproject.extensions

import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}
