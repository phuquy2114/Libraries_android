package com.primarynet.metro.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan


/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 6/26/19.
 */
object StringUtils {
    fun setColorForPath(text: String, paths: Array<String>, color: Int): Spannable {
        val spannable = SpannableString(text)
        for (i in paths.indices) {
            val indexOfPath = spannable.toString().indexOf(paths[i])
            if (indexOfPath == -1) {
                continue
            }
            spannable.setSpan(
                    ForegroundColorSpan(color),
                    indexOfPath,
                    indexOfPath + paths[i].length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            /*
 s.setSpan(new RelativeSizeSpan(1.2f), text.indexOf("A"), text.length(), 0);
 s.setSpan(new StyleSpan(Typeface.NORMAL), text.indexOf("A"), text.length(), 0);
 s.setSpan(new ForegroundColorSpan(Color.BLACK), text.indexOf("A"), text.length(), 0);
 */
            spannable.setSpan(StyleSpan(Typeface.NORMAL), indexOfPath, indexOfPath + paths[i].length, 0);
        }
        return spannable
    }

    /**
     * replace string
     */
    fun replaceCharacter(char: String): String {
        var translateString = char.replace("[()?:!.,;{}]+", "")
        return translateString
    }
}
