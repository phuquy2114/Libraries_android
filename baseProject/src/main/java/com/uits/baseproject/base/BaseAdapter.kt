package com.uits.baseproject.base

import android.content.Context
import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView

/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 31/08/2018.
 */
abstract class BaseAdapter<VH : RecyclerView.ViewHolder?> constructor(val context: Context) : RecyclerView.Adapter<VH>() {

    public val resources: Resources
        get() = context.resources

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}