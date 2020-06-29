package com.uits.baseproject.widget.loopingviewpager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Class using changing the speed of transition of ViewPager and setCurrentItem
 *
 * @author PhuQuy
 */
public class FixedSpeedScroller extends Scroller {
    private static final int DEFAULT_DURATION = 5000; // value default 5s
    private int mDuration = DEFAULT_DURATION;

    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel, int duration) {
        super(context, interpolator, flywheel);
        mDuration = duration;
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        this(context, interpolator, false, DEFAULT_DURATION);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
