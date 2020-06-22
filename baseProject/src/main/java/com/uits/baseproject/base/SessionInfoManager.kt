package com.uits.baseproject.base

import android.content.Context
import android.content.SharedPreferences

/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 3/25/19.
 */
object SessionInfoManager {
    private lateinit var sharedPref: SharedPreferences

    private val KEY_SAVE_PHONE = "key_save_phone"
    private val KEY_SAVE_USERNAME = "key_save_user_name"

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
     * Set key save phone
     *
     * @param token
     */
    var keySavePhone: String
        get() = sharedPref.getString(KEY_SAVE_PHONE, "")!!
        set(token) = sharedPref.edit().putString(KEY_SAVE_PHONE, token).apply()

    /**
     * Set key save user name
     *
     * @param token
     */
    var keySaveUserName: String
        get() = sharedPref.getString(KEY_SAVE_USERNAME, "")!!
        set(token) = sharedPref.edit().putString(KEY_SAVE_USERNAME, token).apply()

    /**
     * clear share
     */
    fun clear() {
        sharedPref.edit().clear().apply()
    }
}
