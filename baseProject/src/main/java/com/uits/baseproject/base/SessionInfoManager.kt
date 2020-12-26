package com.uits.baseproject.base

import android.content.Context
import android.content.SharedPreferences

/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 3/25/19.
 */
object SessionInfoManager {

    val KEY_SAVE_TOKENT = "key_key_token"

    private lateinit var sharedPref: SharedPreferences

    fun init(context: Context, sharedPref: SharedPreferences) {
        this.sharedPref = sharedPref
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
     * Set key save token
     *
     * @param token
     */
    var keySaveToken: String
        get() = sharedPref.getString(KEY_SAVE_TOKENT, "")!!
        set(token) = sharedPref.edit().putString(KEY_SAVE_TOKENT, token).apply()

    /**
     * clear share
     */
    fun clear() {
        sharedPref.edit().clear().apply()
    }
}
