package com.uits.baseproject.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 2/28/19.
 */
public final class DateTimeUtil {
    /**
     * enum define format type
     */
    public enum FORMAT_TYPE {
        TYPE_1("yyyy/MM/dd");

        private final String value;

        FORMAT_TYPE(String val) {
            value = val;
        }

        public String getValue() {
            return value;
        }
    }

    private DateTimeUtil() {
        // no instance
    }

    public static String getCurrentDateWithFormat(FORMAT_TYPE format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format.getValue(), Locale.getDefault());
        Date currentDate = new Date();
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(currentDate);
    }
}

