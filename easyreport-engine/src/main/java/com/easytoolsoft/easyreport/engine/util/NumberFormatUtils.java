package com.easytoolsoft.easyreport.engine.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

public class NumberFormatUtils {
    private static final Logger logger = LoggerFactory.getLogger(NumberFormatUtils.class);

    public static String format(Object value) {
        return (value == null) ? "" : format(value.toString());
    }

    public static String format(String value) {
        if (!isNumber(value)) {
            return value;
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            return nf.format(nf.parse(value));
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return value;
    }

    public static String percentFormat(Object value) {
        return percentFormat(value.toString(), 2);
    }

    public static String percentFormat(Object value, int decimals) {
        return (value == null) ? "" : percentFormat(value.toString(), decimals);
    }

    public static String percentFormat(String value, int decimals) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(decimals);
        try {
            BigDecimal bd = new BigDecimal(value);
            return nf.format(Double.valueOf(bd.toPlainString()));
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
        }
        return value;
    }

    public static String decimalFormat(Object value) {
        return decimalFormat(value, 4);
    }

    public static String decimalFormat(Object value, int decimals) {
        if (value == null)
            return "";

        String formattedValue = value.toString();
        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(decimals);
        try {
            BigDecimal bd = new BigDecimal(formattedValue);
            formattedValue = nf.format(Double.valueOf(bd.toPlainString()));
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
        }
        return formattedValue;
    }

    public static boolean isNumber(String value) {
        return Pattern.matches("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)?(?:\\.\\d+)?$", value);
    }
}
