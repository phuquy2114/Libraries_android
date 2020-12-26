package com.uits.baseproject.utils;

import android.content.Context;
import android.util.Log;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Class support error crash app.
 *
 * @author PHUQUY.
 * @since 05-Jan-2017.
 */
public class CrashAppImplement implements UncaughtExceptionHandler {
    private static final String LOG = CrashAppImplement.class.getName();
    private static CrashAppImplement crashHandler;

    private Context context;

    /**
     * Constructor of class.
     */
    private CrashAppImplement() {
    }

    /**
     * @return static.
     */
    public static synchronized CrashAppImplement getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashAppImplement();
        }

        return crashHandler;
    }

    /**
     * @param context Value context.
     */
    public void init(Context context) {
        this.context = context;
    }

    /**
     * @param thread Thread.
     * @param ex     Error.
     */
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(LOG, "uncaughtException" + ex.getMessage());
        ClearDatabase.clear(context);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
