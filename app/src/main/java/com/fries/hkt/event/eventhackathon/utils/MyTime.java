package com.fries.hkt.event.eventhackathon.utils;

import java.util.Calendar;

/**
 * Created by tmq on 12/03/2017.
 */

public class MyTime {
    private static Calendar calendar = Calendar.getInstance();

    public static String toString_10(int x) {
        if (x == 0) return "";
        return (x >= 10) ? (x + "") : ("0" + x);
    }


//    public static String timeToHourAndMin() {
//        String vl = "";
//    }


    public static String timeToHour(long milliseconds) {
        calendar.setTimeInMillis(milliseconds);
        int hours = calendar.get(Calendar.HOUR);
        int mins = calendar.get(Calendar.MINUTE);
        String am_pm = (calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return toString_10(hours) + "h";
    }

    public static String timeToMin(long milliseconds) {
        calendar.setTimeInMillis(milliseconds);
        int hours = calendar.get(Calendar.HOUR);
        int mins = calendar.get(Calendar.MINUTE);
        String am_pm = (calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return toString_10(mins) + "";
    }

    public static String timeToDay(long milliseconds) {
        calendar.setTimeInMillis(milliseconds);
        int hours = calendar.get(Calendar.HOUR);
        int mins = calendar.get(Calendar.MINUTE);
        String am_pm = (calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return toString_10(day) + "";
    }

    public static String timeToMonth(long milliseconds) {
        calendar.setTimeInMillis(milliseconds);
        int hours = calendar.get(Calendar.HOUR);
        int mins = calendar.get(Calendar.MINUTE);
        String am_pm = (calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return toString_10(month) + "";
    }

    public static String timeToDayHour(long milliseconds) {
        calendar.setTimeInMillis(milliseconds);
        int hours = calendar.get(Calendar.HOUR);
        int mins = calendar.get(Calendar.MINUTE);
        String am_pm = (calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return toString_10(hours) + "h:" + toString_10(mins) + ", " + toString_10(day) + "/" + toString_10(month);
    }

    public static String longtime(long milliseconds) {
        milliseconds/=1000;
        long hours = milliseconds/3600;
        long mins = (milliseconds%3600)/60;
        return hours + "h:" + mins;
    }

    public static boolean changeDay(long t1, long t2) {
        calendar.setTimeInMillis(t1);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        int month1 = calendar.get(Calendar.MONTH) + 1;
        int year1 = calendar.get(Calendar.YEAR);

        calendar.setTimeInMillis(t2);
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);
        int month2 = calendar.get(Calendar.MONTH) + 1;
        int year2 = calendar.get(Calendar.YEAR);

        if (year2 > year1) return true;
        if (month2 > month1) return true;
        if (day2 > day1) return true;
        return false;
    }
}
