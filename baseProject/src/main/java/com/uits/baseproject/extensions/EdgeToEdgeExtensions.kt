package com.uits.baseproject.extensions

import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun View.applyInsets(
    toTop: Boolean = false,
    toBottom: Boolean = false,
    toStart: Boolean = false,
    toEnd: Boolean = false
) {
    val initialPadding = Rect(
        paddingLeft, paddingTop, paddingRight, paddingBottom
    )
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

        view.setPadding(
            initialPadding.left + if (toStart) systemInsets.left else 0,
            initialPadding.top + if (toTop) systemInsets.top else 0,
            initialPadding.right + if (toEnd) systemInsets.right else 0,
            initialPadding.bottom + if (toBottom) systemInsets.bottom else 0
        )

        insets
    }
    requestApplyInsets()
}

/**
 * Applies top system bar insets so this view's content fits below the status bar (edge-to-edge safe).
 */
fun View.fitsToTopEdge() {
    applyInsets(toTop = true)
}

/**
 * Applies bottom system bar insets so this view's content fits above the navigation bar (edge-to-edge safe).
 */
fun View.fitsToBottomEdge() {
    applyInsets(toBottom = true)
}