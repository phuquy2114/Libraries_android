@file:Suppress("unused")

package com.uits.baseproject.utils

import android.util.Base64

/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 5/29/19.
 */

val ByteArray.encodeBase64: String
    get() = Base64.encode(this, Base64.NO_WRAP).toString(charset("UTF-8"))

val String.decodeBase64: ByteArray
    get() = Base64.decode(this, Base64.NO_WRAP)