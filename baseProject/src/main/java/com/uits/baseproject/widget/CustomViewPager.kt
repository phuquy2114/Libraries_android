package com.primarynet.metro.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 7/18/19.
 */
class CustomViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var mEnabled = false

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (mEnabled) {
            true -> super.onTouchEvent(event)
            false -> false
        }

    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return when (mEnabled) {
            true -> super.onInterceptTouchEvent(event)
            false -> false
        }

    }

    fun setPagingEnabled(enabled: Boolean) {
        this.mEnabled = enabled
    }
}