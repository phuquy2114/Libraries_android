package com.uits.baseproject.widget.indicator;

import androidx.viewpager.widget.ViewPager;

/**
 * Reference: https://github.com/JakeWharton/ViewPagerIndicator
 *
 * @author PhuQuy
 */
public interface PageIndicator extends ViewPager.OnPageChangeListener {
    void setViewPager(ViewPager view);

    void setCurrentItem(int item);
}
