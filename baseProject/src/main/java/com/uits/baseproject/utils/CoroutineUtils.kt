@file:Suppress("unused")

package com.uits.baseproject.utils

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * @author PhuQuy
 */
val exceptionHandler = CoroutineExceptionHandler { _, exception ->
    Log.e("Quy", "Coroutine Exception Handler, Caught: ${exception.message}")
}

val IO: CoroutineContext = Dispatchers.IO
val DEFAULT: CoroutineContext = Dispatchers.Default
val MAIN: CoroutineContext = Dispatchers.Main
