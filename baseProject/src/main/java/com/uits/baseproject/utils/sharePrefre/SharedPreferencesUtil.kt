package com.uits.baseproject.utils.sharePrefre

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * SharedPreferencesを操作するユーティリティ
 * <BR></BR>
 * Androidの設定値をキー＆バリューで保持するSharedPreferences
 * を操作するユーティリティクラス
 */
object SharedPreferencesUtil {
    /**
     * ロック用オブジェクト
     */
    private val lock = Any()

    /**
     * SharedPreferences
     */
    private lateinit var prefs: SharedPreferences

    fun initialize(context: Context) {
        val TAG = "${context.packageName}.BaseUITSLib_prefs"
        prefs = context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }

    /**
     * キー名に対応したint値を取得する
     *
     * @param key キー名
     * @return int値　キー名が存在しない場合は、0を返却する。
     */
    fun getInt(key: String?): Int {
        return getInt(key, 0)
    }

    /**
     * キーに対応したInt値を取得する
     *
     * @param key キー名
     * @param def キーが存在しない場合の初期値
     * @return キー名に対応した値。ない場合はdefを返却する
     */
    fun getInt(key: String?, def: Int): Int {
        return prefs.getInt(key, def)
    }

    /**
     * SPよりキー名に対応したlong値を取得する
     *
     * @param key キー名
     * @return キー名に対応した値。無い場合は0。
     */
    fun getLong(key: String?, def: Long): Long {
        return prefs.getLong(key, def)
    }

    /**
     * SPよりキー名に対応したfloat値を取得する
     *
     * @param key キー名
     * @return キー名に対応した値。無い場合は0。
     */
    fun getFloat(key: String?): Float {
        return prefs.getFloat(key, 0f)
    }

    /**
     * キーに対応したfloat値を取得する
     *
     * @param key キー名
     * @param def キーが存在しない場合の初期値
     * @return キー名に対応した値。ない場合はdefを返却する
     */
    fun getFloat(key: String?, def: Float): Float {
        return try {
            prefs.getFloat(key, def)
        } catch (e: Exception) {

            // 再配置すする
            prefs.edit().putFloat(key, def).apply()
            def
        }
    }

    fun getDouble(key: String?, def: Double): Double {
        return try {
            java.lang.Double.longBitsToDouble(
                prefs.getLong(
                    key,
                    java.lang.Double.doubleToLongBits(def)
                )
            )
        } catch (e: Exception) {

            // 再配置すする
            prefs.edit().putLong(key, java.lang.Double.doubleToRawLongBits(def)).apply()
            def
        }
    }

    /**
     * キー名に対応したboolean値を取得する
     *
     * @param key キー名
     * @param def キーが存在しない場合の初期値
     * @return キー名に対応した値。ない場合はdefを返却する
     */
    fun getBoolean(key: String?, def: Boolean): Boolean {
        return prefs.getBoolean(key, def)
    }

    /**
     * プリファレンスから指定のboolean値を取得する
     *
     * @param key キー名
     * @return boolean値　キー名が存在しない場合はfalseを返却
     */
    fun getBoolean(key: String?): Boolean {
        return getBoolean(key, false)
    }

    /**
     * プリファレンスから指定のboolean値を取得する
     *
     * @param key キー名
     * @return boolean値　キー名が存在しない場合はtrueを返却
     */
    fun getBooleanNonKeyTrue(key: String?): Boolean {
        return getBoolean(key, true)
    }

    /**
     * プリファレンスから指定のString値を取得する
     *
     * @param key キー名
     * @return String値　キー名が存在しない場合は""(空文字)を返却
     */
    fun getString(key: String?): String {
        return getString(key, "")
    }

    /**
     * プリファレンスから指定のString値を取得する
     *
     * @param key キー名
     * @param def キー名が存在しない場合の返却値
     * @return キー名に対応した値。ない場合はdefを返却する
     */
    fun getString(key: String?, def: String): String {
        return prefs.getString(key, def) ?: def
    }

    /**
     * キー名に紐づくString値を格納する
     *
     * @param key   キー名
     * @param value 値
     */
    fun setString(key: String?, value: String?) {
        synchronized(lock) {
            val editor = prefs.edit()
            editor.putString(key, value)
            editor.apply()
        }
    }

    /**
     * キー名に紐づくint値を格納する
     *
     * @param key   キー名
     * @param value 値
     */
    fun setInt(key: String?, value: Int) {
        synchronized(lock) {
            val editor = prefs.edit()
            editor.putInt(key, value)
            editor.apply()
        }
    }

    /**
     * キー名に紐づくlong値を格納する
     *
     * @param key   キー名
     * @param value 値
     */
    fun setLong(key: String?, value: Long) {
        synchronized(lock) {
            val editor = prefs.edit()
            editor.putLong(key, value)
            editor.apply()
        }
    }

    /**
     * キー名に紐づくlong値を格納する
     *
     * @param key   キー名
     * @param value 値
     */
    fun setFloat(key: String?, value: Float) {
        synchronized(lock) {
            val editor = prefs.edit()
            editor.putFloat(key, value)
            editor.apply()
        }
    }

    /**
     * キー名に紐づくdouble値を格納する
     *
     * @param key   キー名
     * @param value 値
     */
    fun setDouble(key: String?, value: Double) {
        synchronized(lock) {
            val editor = prefs.edit()
            editor.putLong(key, java.lang.Double.doubleToRawLongBits(value))
            editor.apply()
        }
    }

    /**
     * キー名に紐づくboolean値を格納する
     *
     * @param key   キー名
     * @param value 値
     */
    fun setBoolean(key: String?, value: Boolean) {
        synchronized(lock) {
            val editor = prefs.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }
    }

    fun remove(key: String?) {
        synchronized(lock) {
            val editor = prefs.edit()
            editor.remove(key)
            editor.apply()
        }
    }

    operator fun contains(key: String?): Boolean {
        return prefs.contains(key)
    }

    fun clear() {
        val editor = prefs.edit()
        editor.clear() // Clears all the data
        editor.apply()
    }

    fun <T> saveCacheByJson(key: String?, list: List<T>?) {
        val json = Gson().toJson(list, object : TypeToken<List<T>?>() {}.type)
        setString(key, json)
    }

    fun <T> loadCacheByEntity(key: String?, token: TypeToken<List<T>?>): List<T> {
        val json = getString(key)
        var list = Gson().fromJson<List<T>>(json, token.type)
        if (list == null) {
            list = ArrayList()
        }
        return list
    }
}