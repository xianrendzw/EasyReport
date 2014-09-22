package org.easyframework.report.engine.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具类
 */
public class DateUtils {
	public static Date add(int days) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR, days);
		return now.getTime();
	}

	public static String add(int days, String formatPattern) {
		Date date = add(days);
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(date);
	}

	public static String add(int intDate, int days) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Calendar cd = Calendar.getInstance();
			cd.setTime(format.parse(String.valueOf(intDate)));
			cd.add(Calendar.DAY_OF_YEAR, days);
			return format.format(cd.getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String add(String date, int days, String pattern) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Calendar cd = Calendar.getInstance();
			cd.setTime(format.parse(date));
			cd.add(Calendar.DAY_OF_YEAR, days);
			return format.format(cd.getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getNowFormat(String formatPattern) {
		Calendar now = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(now.getTime());
	}

	public static String format(Object date, String formatPattern) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date theDate = format.parse(date.toString());
			format.applyPattern(formatPattern);
			return format.format(theDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("deprecation")
	public static String UTCFormat(Object date, String formatPattern) {
		try {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date now = Calendar.getInstance().getTime();
			Date theDate = format.parse(date.toString());
			theDate.setHours(now.getHours());
			theDate.setMinutes(now.getMinutes());
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
			format.applyPattern(formatPattern);
			return format.format(theDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String format(Date date, String formatPattern) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(date);
	}

	public static String addHours(int intDate, int hours) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Calendar cd = Calendar.getInstance();
			cd.setTime(format.parse(String.valueOf(intDate)));
			cd.add(Calendar.HOUR_OF_DAY, hours);
			return format.format(cd.getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String addHours(String date, int hours, String pattern) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Calendar cd = Calendar.getInstance();
			cd.setTime(format.parse(date));
			cd.add(Calendar.HOUR_OF_DAY, hours);
			return format.format(cd.getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
