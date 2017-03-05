package com.easytoolsoft.easyreport.common.util;

/**
 * @author tomdeng
 */
public class CheckUtils {
    public static int validateNull(Integer orginal, int defaultValue) {
        return (orginal == null) ? defaultValue : orginal;
    }

    public static long validateNull(Long orginal, long defaultValue) {
        return (orginal == null) ? defaultValue : orginal;
    }

    public static boolean validateNull(Boolean orginal, boolean defaultValue) {
        return (orginal == null) ? defaultValue : orginal;
    }

    public static byte validateNull(Byte orginal, byte defaultValue) {
        return (orginal == null) ? defaultValue : orginal;
    }
}
