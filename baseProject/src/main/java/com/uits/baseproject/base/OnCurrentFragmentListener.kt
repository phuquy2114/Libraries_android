package com.uits.baseproject.base

import androidx.fragment.app.Fragment

/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 3/11/19.
 */
interface OnCurrentFragmentListener {
    fun onCurrentFragment(fragment: Fragment)

    fun onBackFragment()
}
