package com.uits.baseproject.baselifecycle

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * BaseViewHolder
 * Copyright © 2019 UITS CO.,LTD
 * Created PHUQUY on 7/9/20.
 **/
abstract class BaseViewHolder<D>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(item: D)

}