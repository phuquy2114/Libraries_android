package com.uits.baseproject.base

import android.content.Context
import android.content.SharedPreferences

/**
 * A session manager where saving all states of application.
 *
 * https://github.com/lomza/sharedpreferences-in-kotlin/blob/master/app/src/main/java/com/lomza/spinkotlin/AppPreferences.kt
 * SharedPreferences
 *
 * @author QuyDP
 */
object SessionManager {

    private lateinit var sharedPref: SharedPreferences
    private val SHARED_PREFERENCES_NAME = "com.uits.baseproject.base.PREFERENCE_FILE_KEY"

    fun init(context: Context) {
        sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        SessionInfoManager.init(context, sharedPref)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }


    /**
     * clear share
     */
    fun clear() {
        sharedPref.edit().clear().apply()
    }
}
