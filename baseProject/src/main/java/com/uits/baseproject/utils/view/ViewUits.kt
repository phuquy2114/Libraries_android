package com.uits.baseproject.utils.view

import android.os.SystemClock
import android.view.View
import java.util.WeakHashMap

abstract class DebouncedOnClickListener(private val minInterval: Long = 1000L) : View.OnClickListener {

    private val lastClickMap = WeakHashMap<View, Long>()

    abstract fun onDebouncedClick(v: View)

    override fun onClick(v: View) {
        val previousClickTimestamp = lastClickMap[v]
        val currentTimestamp = SystemClock.uptimeMillis()

        lastClickMap[v] = currentTimestamp
        if (previousClickTimestamp == null || currentTimestamp - previousClickTimestamp > minInterval) {
            onDebouncedClick(v)
        }
    }
}

fun View.setDebouncedOnClickListener(action: (() -> Unit)? = null) {
    setOnClickListener(object : DebouncedOnClickListener() {
        override fun onDebouncedClick(v: View) {
            action?.invoke()
        }
    })
}

fun View.setSingleClickListener(debounceTime: Long = 500L, onClick: (View) -> Unit) {
    var lastClickTime = 0L

    setOnClickListener { view ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > debounceTime) {
            lastClickTime = currentTime
            onClick(view)
        }
    }
}