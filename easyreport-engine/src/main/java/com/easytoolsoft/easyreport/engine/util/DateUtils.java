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
 *
 * @author tomdeng
 */
public class DateUtils {
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

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
    public static String getNow(final String pattern) {
        final Calendar now = Calendar.getInstance();
        final SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(now.getTime());
    }

    /**
     * 以指定日期格式返回日期字符串
     *
     * @param date    yyyy-MM-dd格式的日期字符串
     * @param pattern 日期时间格式(如:yyyy-MM-dd,MM-dd-yyyy等)
     * @return 指定日期格式返回日期字符串
     */
    public static String getDate(final String date, final String pattern) {
        try {
            final SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
            final Date theDate = format.parse(date);
            format.applyPattern(pattern);
            return format.format(theDate);
        } catch (final ParseException e) {
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
    public static String getDate(final Date date, final String pattern) {
        final SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 以指定日期格式返回UTC日期字符串
     *
     * @param date    yyyy-MM-dd格式的日期字符串
     * @param pattern 日期时间格式(如:yyyy-MM-dd,MM-dd-yyyy等)
     * @return 指定日期格式的UTC日期字符串
     */
    public static String getUtcDate(final String date, final String pattern) {
        try {
            final SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
            final Date now = Calendar.getInstance().getTime();
            final Date theDate = format.parse(date);
            theDate.setHours(now.getHours());
            theDate.setMinutes(now.getMinutes());
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            format.applyPattern(pattern);
            return format.format(theDate);
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 当前日期增加days天
     *
     * @param days 天数
     * @return 增加days天后的日期
     */
    public static Date add(final int days) {
        final Calendar now = Calendar.getInstance();
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
    public static String add(final int days, final String pattern) {
        final Date date = add(days);
        final SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 给指定日期增加days天
     *
     * @param date yyyyMMdd格式的日期
     * @param days 天数
     * @return yyyyMMdd格式的日期字符串
     */
    public static String add(final int date, final int days) {
        try {
            final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            final Calendar cd = Calendar.getInstance();
            cd.setTime(format.parse(String.valueOf(date)));
            cd.add(Calendar.DAY_OF_YEAR, days);
            return format.format(cd.getTime());
        } catch (final ParseException e) {
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
    public static String add(final String date, final int days, final String pattern) {
        try {
            final SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
            final Calendar cd = Calendar.getInstance();
            cd.setTime(format.parse(date));
            cd.add(Calendar.DAY_OF_YEAR, days);
            return new SimpleDateFormat(pattern).format(cd.getTime());
        } catch (final ParseException e) {
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
    public static String addHours(final int date, final int hours) {
        try {
            final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            final Calendar cd = Calendar.getInstance();
            cd.setTime(format.parse(String.valueOf(date)));
            cd.add(Calendar.HOUR_OF_DAY, hours);
            return format.format(cd.getTime());
        } catch (final ParseException e) {
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
    public static String addHours(final String date, final int hours, final String pattern) {
        try {
            final SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
            final Calendar cd = Calendar.getInstance();
            cd.setTime(format.parse(date));
            cd.add(Calendar.HOUR_OF_DAY, hours);
            return new SimpleDateFormat(pattern).format(cd.getTime());
        } catch (final ParseException e) {
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
    public static long getDateDiff(final String startDt, final String endDt) {
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
    public static long getDateDiff(final String startDt, final String endDt, final String pattern) {
        final SimpleDateFormat sd = new SimpleDateFormat(pattern);
        final long nd = 1000 * 24 * 60 * 60;
        try {
            final long diff = sd.parse(endDt).getTime() - sd.parse(startDt).getTime();
            return diff / nd;
        } catch (final ParseException e) {
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
    public static List<String> getDateList(final String startDt, final String endDt) throws ParseException {
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
    public static List<String> getDateList(final String startDt, final String endDt, final String pattern)
        throws ParseException {
        final SimpleDateFormat sd = new SimpleDateFormat(pattern);
        final List<String> dateList = new ArrayList<>();

        dateList.add(startDt);
        final Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(sd.parse(startDt));
        final Calendar endCalendar = Calendar.getInstance();
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
