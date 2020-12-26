package com.uits.baseproject.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.uits.baseproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static android.text.format.DateUtils.MINUTE_IN_MILLIS;
import static android.text.format.DateUtils.getRelativeTimeSpanString;

/**
 * Copyright © 2018 UITS  CO., LTD
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

    /**
     * get during time by time format yyyy-mm-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static final String getDuringTime(Context context, String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        if (time == null) {
            return "";
        }
        try {
            return getTimeMinString(context, format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTimeMinString(Context context, Date date) {
        Date now = new Date();
        if (now.getTime() - date.getTime() < 60000) {
            return context.getResources().getString(R.string.text_date_time_just_now);
        } else {
            return getRelativeTimeSpanString(date.getTime(), now.getTime(), MINUTE_IN_MILLIS).toString();
        }
    }

    /**
     * get duration of youtube video base on date time String
     *
     * @param context
     * @param time
     * @return
     */
    public static String getDuringTimeYoutube(Context context, String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        if (time == null) {
            return "";
        }
        try {
            return getTimeMinString(context, format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * current date time string
     *
     * @return
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * function to compare 2 timeStamp the same date ?
     *
     * @param firstTime
     * @param secondTime
     * @return
     */
    public static boolean theSameDay(long firstTime, long secondTime) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(firstTime).equals(fmt.format(secondTime));
    }

    /**
     * get time hour format
     *
     * @param time
     * @return
     */
    public static String getHourTime(long time) {
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        return fmt.format(time);
    }

    /**
     * check date time is today or yesterday or day of week
     *
     * @param time
     * @param context
     * @return
     */
    public static String checkDate(long time, Context context) {
        if (DateUtils.isToday(time)) {
            return context.getResources().getString(R.string.text_today);
        } else {
            Calendar c1 = Calendar.getInstance(); // today
            c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday

            Calendar c2 = Calendar.getInstance();
            c2.setTimeInMillis(time);
            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                return context.getResources().getString(R.string.text_yesterday);
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd (EEE)");
                return formatter.format(time);
            }
        }
    }

    /**
     * check date time is today or yesterday or day of week
     *
     * @param time
     * @param context
     * @return
     */
    public static String getDateDetailHistory(long time, Context context) {
        if (DateUtils.isToday(time)) {
            return context.getResources().getString(R.string.text_today);
        } else {
            Calendar c1 = Calendar.getInstance(); // today
            c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday

            Calendar c2 = Calendar.getInstance();
            c2.setTimeInMillis(time);
            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                return context.getResources().getString(R.string.text_yesterday);
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                return formatter.format(time);
            }
        }
    }

    /**
     * get history time date
     *
     * @param context
     * @param time
     * @return
     */
    public static String getDateDetailHistory(Context context, Date time) {
        if (DateUtils.isToday(time.getTime())) {
            return context.getResources().getString(R.string.text_today);
        } else {
            Calendar c1 = Calendar.getInstance(); // today
            c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday

            Calendar c2 = Calendar.getInstance();
            c2.setTimeInMillis(time.getTime());
            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                return context.getResources().getString(R.string.text_yesterday);
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat("EEE - yyyy/MM/dd");
                return formatter.format(time);
            }
        }
    }

    /**
     * get Date String of call history
     *
     * @param time
     * @param context
     * @return
     */
    public static String getDateHistoryCall(long time, Context context) {
        if (DateUtils.isToday(time)) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            return formatter.format(time);
        } else {
            //get Calender today
            Calendar today = Calendar.getInstance();
            today.setTime(new Date());
            //get Calender yesterday
            Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DAY_OF_YEAR, -1); // yesterday
            //get Calender selected time
            Calendar timeDate = Calendar.getInstance();
            timeDate.setTimeInMillis(time);
            //check condition
            if (timeDate.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && timeDate.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
                return context.getResources().getString(R.string.text_yesterday);
            } else if (timeDate.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                    && timeDate.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                    && timeDate.get(Calendar.DAY_OF_MONTH) - today.get(Calendar.DAY_OF_MONTH) < 6) {
                SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
                return formatter.format(time);
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM");
                return formatter.format(time);
            }
        }
    }

    /**
     * @param context
     * @param time
     * @return
     */
    public static String getStringDataTimeISO8601(Context context, String time) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

        Date d = null;
        try {
            d = input.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }

    /**
     * * It's return date  before one week timestamp
     * *  like return
     * *  1 day ago
     * *  2 days ago
     * *  5 days ago
     * *  21 April 2019
     * *
     *
     * @param dataDate
     * @return
     */
    public static String covertTimeToText(String dataDate) {

        String convTime = null;
        String prefix = "";
        String suffix = "Ago";

        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                convTime = second + " Seconds " + suffix;
            } else if (minute < 60) {
                convTime = minute + " Minutes " + suffix;
            } else if (hour < 24) {
                convTime = hour + " Hours " + suffix;
            } else if (day >= 7) {
                if (day > 360) {
                    convTime = (day / 360) + " Years " + suffix;
                } else if (day > 30) {
                    convTime = (day / 30) + " Months " + suffix;
                } else {
                    convTime = (day / 7) + " Week " + suffix;
                }
            } else if (day < 7) {
                convTime = day + " Days " + suffix;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;
    }
}

