package com.uits.baseproject.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

/**
 * Copyright © 2018 UITS CO., LTD
 * Created by PHUQUY on 11/27/17.
 */
public class ClearDatabase {

    private static String TAG = ClearDatabase.class.getSimpleName();
    @SuppressLint("SdCardPath")
    private static String DB_PATH = "/data/data/vn.uits.ytsk/databases/";

    /**
     * clear database
     *
     * @param context
     */
    public static void clear(Context context) {
        Log.d(TAG, "clear: ");
    }
}
