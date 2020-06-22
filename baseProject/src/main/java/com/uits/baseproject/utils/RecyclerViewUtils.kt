package com.primarynet.dtems.utils

import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewUtils {

    /**
     * set up horizontal for recycler view
     *
     * @param context
     * @param recyclerView
     * @return
     */
    fun setUpHorizontal(context: Context, recyclerView: RecyclerView): RecyclerView {
        val mLayout = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = mLayout
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        return recyclerView
    }

    /**
     * set up vertical for recycler view
     *
     * @param context
     * @param recyclerView
     * @return
     */
    fun setUpVertical(context: Context, recyclerView: RecyclerView): RecyclerView {
        val mLayout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayout
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        return recyclerView
    }

    /**
     * recyclerView fill data from left to right
     *
     * @param context
     * @param recyclerView
     * @param column
     * @return
     */
    fun setUpGrid(context: Context, recyclerView: RecyclerView, column: Int): RecyclerView {
        val mLayout = GridLayoutManager(context, column)
        recyclerView.layoutManager = mLayout
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        return recyclerView
    }

    companion object {
        private var mNewInstance: RecyclerViewUtils? = null
        /**
         * single ton
         *
         * @return
         */
        fun Create(): RecyclerViewUtils {
            if (mNewInstance == null) {
                mNewInstance = RecyclerViewUtils()
            }
            return mNewInstance as RecyclerViewUtils
        }
    }
}
