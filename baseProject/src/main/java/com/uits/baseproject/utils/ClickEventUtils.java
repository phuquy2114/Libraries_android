package com.uits.baseproject.utils;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 2/28/19.
 */
public class ClickEventUtils {
    private static final int CLICK_LOCK_INTERVAL = 500;

    private static boolean sIsLocked;

    private static Runnable mClickLockRunnale = new Runnable() {
        @Override
        public void run() {
            sIsLocked = false;
        }
    };

    private static Handler mHandler = new Handler();

    /**
     * mHandler click CLICK_LOCK_INTERVAL
     *
     * @return
     */
    public synchronized static boolean isLocked() {
        if (sIsLocked) return true;
        mHandler.postDelayed(mClickLockRunnale, CLICK_LOCK_INTERVAL);
        sIsLocked = true;
        return false;
    }

    /**
     * mHandler click
     *
     * @param longClick
     * @return
     */
    public synchronized static boolean isLocked(int longClick) {
        if (sIsLocked) return true;
        mHandler.postDelayed(mClickLockRunnale, longClick);
        sIsLocked = true;
        return false;
    }

    /**
     * Lock screen don't click view
     *
     * @param view    view
     * @param enabled if enabled = true set click, else set don't click
     */
    public static void setViewAndChildrenEnable(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) view;
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                View child = linearLayout.getChildAt(i);
                setViewAndChildrenEnable(child, enabled);
            }
        } else if (view instanceof RelativeLayout) {
            RelativeLayout relativeLayout = (RelativeLayout) view;
            for (int i = 0; i < relativeLayout.getChildCount(); i++) {
                View child = relativeLayout.getChildAt(i);
                setViewAndChildrenEnable(child, enabled);
            }
        }
    }

}
