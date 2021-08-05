package com.uits.baseproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Utilities for getting screen size or convert "dp" to "px".
 */
public final class ScreenUtil {
    private static final String TAG = ScreenUtil.class.getSimpleName();

    private ScreenUtil() {
        // no instance
    }

    /**
     * Check on/off sensor
     *
     * @param context
     * @return
     */
    public static boolean isSensorRotationEnable(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
    }

    /**
     * @param context is current context
     * @param spValue is value you want to convert for
     * @return return value in pixel
     */
    public static int convertSPtoPX(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return Math.round(spValue * fontScale);
    }

    /**
     * Convert dp to pixel
     *
     * @param dp      dp value
     * @param context content
     * @return return value in pixel
     */
    public static int convertDpToPixel(Context context, int dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (dp * (metrics.densityDpi / 160f));
    }

    /**
     * convert pixel to dp
     *
     * @param context
     * @param pixel
     * @return
     */
    public static float convertPixelToDp(Context context, float pixel) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = pixel / metrics.density;
        return dp;
    }

    /**
     * Class that manage the size of screen.
     */
    public static class ScreenSize {
        private int width;
        private int height;
    }

    /**
     * Disable touch screen
     */
    public static void disableTouchOnScreen(Activity activity, boolean isDisable) {
        if (activity == null) {
            return;
        }
        if (isDisable) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    /**
     * http://stackoverflow.com/questions/19800422/disabling-all-child-views-inside-the-layout
     * http://stackoverflow.com/questions/7687784/how-to-disable-enable-all-children-on-linearlayout-in-android
     * disable viewgroup
     *
     * @param view
     */
    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);

        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    /**
     * https://gist.github.com/hamakn/8939eb68a920a6d7a498
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(@NonNull Context context) {
        // status bar height
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * https://gist.github.com/hamakn/8939eb68a920a6d7a498
     *
     * @param context
     * @return
     */
    public static int getActionBarHeight(@NonNull Context context) {
        // action bar height
        int actionBarHeight = 0;
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize}
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return actionBarHeight;
    }

    /**
     * https://gist.github.com/hamakn/8939eb68a920a6d7a498
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(@NonNull Context context) {
        // navigation bar height
        int navigationBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }

    /**
     * http://stackoverflow.com/questions/28303342/application-content-goes-behind-the-navigation-bar-in-android-l
     * get bar size port
     *
     * @param activity
     * @return
     */
    public static int getSoftButtonsBarSizePort(Activity activity) {
        // getRealMetrics is only available with API 17 and +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }
}
