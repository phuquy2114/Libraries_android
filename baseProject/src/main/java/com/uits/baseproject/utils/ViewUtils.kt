@file:Suppress("unused")

package com.uits.baseproject.utils

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.*
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import com.google.android.material.tabs.TabLayout

/**
 * @author PhuQuy
 */

fun View.visible() = if (visibility != View.VISIBLE) visibility = View.VISIBLE else Unit

fun View.invisible() = if (visibility != View.INVISIBLE) visibility = View.INVISIBLE else Unit

fun View.gone() = if (visibility != View.GONE) visibility = View.GONE else Unit

val View.animateCompat: ViewPropertyAnimatorCompat
    get() = ViewCompat.animate(this)

fun View.setBackgroundDrawableCompat(drawable: Drawable) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        background = drawable
    } else {
        @Suppress("DEPRECATION")
        setBackgroundDrawable(drawable)
    }
}

fun LayoutInflater.inflate(@LayoutRes layoutResId: Int): View = inflate(layoutResId, null)

fun ProgressBar.setColor(@ColorRes colorResId: Int) =
    DrawableCompat.setTint(indeterminateDrawable, context.getColorCompat(colorResId))

@Suppress("DEPRECATION")
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
fun SeekBar.setColor(@ColorRes colorResId: Int) {
    progressDrawable.setColorFilter(context.getColorCompat(colorResId), PorterDuff.Mode.SRC_ATOP)
    thumb.setColorFilter(context.getColorCompat(colorResId), PorterDuff.Mode.SRC_ATOP)
}

fun MenuItem.iconifyTitle(context: Context, title: String, @DrawableRes iconResId: Int) {
    SpannableStringBuilder("#  $title ").apply {
        setSpan(
            ImageSpan(context, iconResId, DynamicDrawableSpan.ALIGN_CENTER),
            0,
            1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }.let { setTitle(it) }
}

fun TabLayout.disableTextAllCaps() {
    val viewGroup = getChildAt(0) as ViewGroup
    val tabsCount = viewGroup.childCount
    for (j in 0 until tabsCount) {
        val viewGroupChildAt = viewGroup.getChildAt(j) as ViewGroup
        val tabChildCount = viewGroupChildAt.childCount
        for (i in 0 until tabChildCount) {
            val tabViewChild = viewGroupChildAt.getChildAt(i)
            if (tabViewChild is TextView) {
                tabViewChild.isAllCaps = false
            }
        }
    }
}

fun NumberPicker.setDividerColor(@ColorInt color: Int) {
    val pickerFields = NumberPicker::class.java.declaredFields
    for (pf in pickerFields) {
        if (pf.name == "mSelectionDivider") {
            pf.isAccessible = true
            try {
                val colorDrawable = ColorDrawable(color)
                pf.set(this, colorDrawable)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            break
        }
    }
}

