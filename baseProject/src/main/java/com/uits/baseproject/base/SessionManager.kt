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
    private val SHARED_PREFERENCES_NAME = "com.primarynet.SmartBus.PREFERENCE_FILE_KEY"

    private val KEY_SAVE_TOKEN = "key_save_device_token"
    private val KEY_SAVE_VERSION = "key_save_device_version_app"
    private val KEY_SAVE_SESSION_ID = "key_save_session_id"
    private val KEY_SAVE_COUNT_BADGE = "key_save_count_badge"
    private val KEY_SAVE_ID = "key_save_id"
    private val KEY_SAVE_AUTO_LOGIN = "key_auto_login"

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
     * get key save mToken
     *
     * @return
     */
    /**
     * Set key save token
     *
     * @param token
     */
    var keySaveToken: String
        get() = sharedPref.getString(KEY_SAVE_TOKEN, "")!!
        set(token) = sharedPref.edit().putString(KEY_SAVE_TOKEN, token).apply()

    /**
     * Set key save token
     *
     * @param token
     */
    var keySaveVersion: String
        get() = sharedPref.getString(KEY_SAVE_VERSION, "")!!
        set(token) = sharedPref.edit().putString(KEY_SAVE_VERSION, token).apply()

    /**
     * get key save mToken
     *
     * @return
     */
    val keySaveSession: String
        get() = sharedPref.getString(KEY_SAVE_SESSION_ID,"")!!

    /**
     * get key save mToken
     *
     * @return
     */
    val keySaveSizeCountBadge: Int
        get() = sharedPref.getInt(KEY_SAVE_COUNT_BADGE, 0)

    /**
     * get key save mToken
     *
     * @return
     */
    val keySaveId: String
        get() = sharedPref.getString(KEY_SAVE_ID, "")!!

    /**
     * Set key save token
     *
     * @param size
     */
    fun setKeySaveSession(size: String) {
        sharedPref.edit().putString(KEY_SAVE_SESSION_ID, size).apply()
    }

    /**
     * Set key save count badge
     *
     * @param size
     */
    fun setKeySaveCountBadge(size: Int) {
        sharedPref.edit().putInt(KEY_SAVE_COUNT_BADGE, size).apply()
    }

    /**
     * Set key save id
     *
     * @param id
     */
    fun setKeySaveId(id: String) {
        sharedPref.edit().putString(KEY_SAVE_ID, id).apply()
    }

    /**
     * auto login
     */
    var autoLogin: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = sharedPref.getBoolean(KEY_SAVE_AUTO_LOGIN, false)
        // custom setter to save a preference back to preferences file
        set(value) = sharedPref.edit {
            it.putBoolean(KEY_SAVE_AUTO_LOGIN, value)
        }


    /**
     * clear share
     */
    fun clear() {
        sharedPref.edit().clear().apply()
    }
}
