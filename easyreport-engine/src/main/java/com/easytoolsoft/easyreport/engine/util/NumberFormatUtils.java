package com.easytoolsoft.easyreport.engine.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tomdeng
 */
public class NumberFormatUtils {
    private static final Logger logger = LoggerFactory.getLogger(NumberFormatUtils.class);

    public static String format(final Object value) {
        return (value == null) ? "" : format(value.toString());
    }

    public static String format(final String value) {
        if (!isNumber(value)) {
            return value;
        }
        final NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            return nf.format(nf.parse(value));
        } catch (final ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return value;
    }

    public static String percentFormat(final Object value) {
        return percentFormat(value.toString(), 2);
    }

    public static String percentFormat(final Object value, final int decimals) {
        return (value == null) ? "" : percentFormat(value.toString(), decimals);
    }

    public static String percentFormat(final String value, final int decimals) {
        final NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(decimals);
        try {
            final BigDecimal bd = new BigDecimal(value);
            return nf.format(Double.valueOf(bd.toPlainString()));
        } catch (final NumberFormatException e) {
            logger.error(e.getMessage(), e);
        }
        return value;
    }

    public static String decimalFormat(final Object value) {
        return decimalFormat(value, 4);
    }

    public static String decimalFormat(final Object value, final int decimals) {
        if (value == null) {
            return "";
        }

        String formattedValue = value.toString();
        final NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(decimals);
        try {
            final BigDecimal bd = new BigDecimal(formattedValue);
            formattedValue = nf.format(Double.valueOf(bd.toPlainString()));
        } catch (final NumberFormatException e) {
            logger.error(e.getMessage(), e);
        }
        return formattedValue;
    }

    public static boolean isNumber(final String value) {
        return Pattern.matches("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)?(?:\\.\\d+)?$", value);
    }
}
