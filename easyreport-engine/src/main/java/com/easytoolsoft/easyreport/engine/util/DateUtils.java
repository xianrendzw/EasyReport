package com.easytoolsoft.easyreport.engine.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 日期工具类
 */
public class DateUtils {
    private static final String YYYY_MM_DD="yyyy-MM-dd";

    /**
     * 获取当前时间的yyyy-MM-dd格式字符串
     *
     * @return 当前时间的yyyy-MM-dd格式字符串
     */
    public static String getNow() {
        return getNow(YYYY_MM_DD);
    }

    /**
     * 获取当前时间指定格式字符串
     *
     * @param pattern 日期时间格式(如:yyyy-MM-dd,MM-dd-yyyy等)
     * @return 当前时间指定格式字符串
     */
    public static String getNow(String pattern) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(now.getTime());
    }

    /**
     * 以指定日期格式返回日期字符串
     *
     * @param date    yyyy-MM-dd格式的日期字符串
     * @param pattern 日期时间格式(如:yyyy-MM-dd,MM-dd-yyyy等)
     * @return 指定日期格式返回日期字符串
     */
    public static String getDate(String date, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
            Date theDate = format.parse(date);
            format.applyPattern(pattern);
            return format.format(theDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 以指定日期格式返回日期字符串
     *
     * @param date    Date类型
     * @param pattern 日期时间格式(如:yyyy-MM-dd,MM-dd-yyyy等)
     * @return 指定格式的日期字符串
     */
    public static String getDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 以指定日期格式返回UTC日期字符串
     *
     * @param date    yyyy-MM-dd格式的日期字符串
     * @param pattern 日期时间格式(如:yyyy-MM-dd,MM-dd-yyyy等)
     * @return 指定日期格式的UTC日期字符串
     */
    @SuppressWarnings("deprecation")
    public static String getUtcDate(String date, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
            Date now = Calendar.getInstance().getTime();
            Date theDate = format.parse(date);
            theDate.setHours(now.getHours());
            theDate.setMinutes(now.getMinutes());
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            format.applyPattern(pattern);
            return format.format(theDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 当前日期增加days天
     *
     * @param days 天数
     * @return 增加days天后的日期
     */
    public static Date add(int days) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, days);
        return now.getTime();
    }

    /**
     * 给当前日期增加days天，并以指定日期格式返回的字符串
     *
     * @param days    天数
     * @param pattern 日期时间格式(如:yyyy-MM-dd,MM-dd-yyyy等)
     * @return 指定日期格式的字符串
     */
    public static String add(int days, String pattern) {
        Date date = add(days);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 给指定日期增加days天
     *
     * @param date yyyyMMdd格式的日期
     * @param days 天数
     * @return yyyyMMdd格式的日期字符串
     */
    public static String add(int date, int days) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Calendar cd = Calendar.getInstance();
            cd.setTime(format.parse(String.valueOf(date)));
            cd.add(Calendar.DAY_OF_YEAR, days);
            return format.format(cd.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 给指定日期增加days天，并以指定日期格式返回的字符串
     *
     * @param date    yyyy-MM-dd格式的日期字符串
     * @param days    天数
     * @param pattern 日期时间格式(如:yyyy-MM-dd,MM-dd-yyyy等)
     * @return 指定日期格式的字符串
     */
    public static String add(String date, int days, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
            Calendar cd = Calendar.getInstance();
            cd.setTime(format.parse(date));
            cd.add(Calendar.DAY_OF_YEAR, days);
            return new SimpleDateFormat(pattern).format(cd.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 给指定日期增加hours小时，并以指定日期格式返回的字符串
     *
     * @param date  yyyyMMdd格式的日期
     * @param hours 小时数
     * @return
     */
    public static String addHours(int date, int hours) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Calendar cd = Calendar.getInstance();
            cd.setTime(format.parse(String.valueOf(date)));
            cd.add(Calendar.HOUR_OF_DAY, hours);
            return format.format(cd.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 给指定日期增加hours小时，并以指定日期格式返回的字符串
     *
     * @param date    yyyy-MM-dd格式的日期字符串
     * @param hours   小时数
     * @param pattern 日期时间格式(如:yyyy-MM-dd,MM-dd-yyyy等)
     * @return 指定日期格式的字符串
     */
    public static String addHours(String date, int hours, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
            Calendar cd = Calendar.getInstance();
            cd.setTime(format.parse(date));
            cd.add(Calendar.HOUR_OF_DAY, hours);
            return new SimpleDateFormat(pattern).format(cd.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取两个日期之间的相隔的天数
     *
     * @param startDt 开始日期(yyyy-MM-dd格式)
     * @param endDt   结束日期 (yyyy-MM-dd格式)
     * @return 相隔的天数
     */
    public static long getDateDiff(String startDt, String endDt) {
        return getDateDiff(startDt, endDt, YYYY_MM_DD);
    }

    /**
     * 获取两个日期之间的相隔的天数
     *
     * @param startDt 开始日期(格式必须与pattern一致)
     * @param endDt   结束日期 (格式必须与pattern一致)
     * @param pattern 日期时间格式(如:yyyy-MM-dd,MM-dd-yyyy等)
     * @return 相隔的天数
     */
    public static long getDateDiff(String startDt, String endDt, String pattern) {
        SimpleDateFormat sd = new SimpleDateFormat(pattern);
        long nd = 1000 * 24 * 60 * 60;
        try {
            long diff = sd.parse(endDt).getTime() - sd.parse(startDt).getTime();
            return diff / nd;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取两个日期之间的所有日期列表(包含开始与结束日期)
     *
     * @param startDt 开始日期(yyyy-MM-dd格式)
     * @param endDt   结束日期 (yyyy-MM-dd格式)
     * @return 日期列表(包含开始与结束日期)
     * @throws ParseException
     */
    public static List<String> getDateList(String startDt, String endDt) throws ParseException {
        return getDateList(startDt, endDt, YYYY_MM_DD);
    }

    /**
     * 获取两个日期之间的所有日期列表(包含开始与结束日期)
     *
     * @param startDt 开始日期(格式必须与pattern一致)
     * @param endDt   结束日期 (格式必须与pattern一致)
     * @param pattern 日期时间格式(如:yyyy-MM-dd,MM-dd-yyyy等)
     * @return 日期列表(包含开始与结束日期)
     * @throws ParseException
     */
    public static List<String> getDateList(String startDt, String endDt, String pattern) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat(pattern);
        List<String> dateList = new ArrayList<>();

        dateList.add(startDt);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(sd.parse(startDt));
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(sd.parse(endDt));
        startCalendar.add(Calendar.DAY_OF_YEAR, 1);
        while (startCalendar.before(endCalendar)) {
            dateList.add(sd.format(startCalendar.getTime()));
            startCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        dateList.add(endDt);

        return dateList;
    }
}
