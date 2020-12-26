package com.uits.baseproject.utils

import android.content.Context
import android.text.TextUtils
import com.uits.baseproject.base.SessionInfoManager

/**
 * Login Utils
 * Copyright © 2019 UITS CO.,LTD
 * Created PHUQUY on 6/23/20.
 **/
object LoginUtils {

    /**
     * check token is login
     */
    fun isLogin(context: Context?): Boolean {
        return !TextUtils.isEmpty(SessionInfoManager.keySaveToken)
    }
}