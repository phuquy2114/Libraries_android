package com.uits.baseproject.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 2/28/19.
 */
abstract class BaseHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindData(context: Context, listData: List<T>, position: Int)

    fun bindDataPayload(context: Context, data: T, position: Int) {}

    fun bindListData(context: Context, listData: List<T>) {

    }

    companion object {

        protected val TAG = "BaseHolder"
    }
}

