package com.easytoolsoft.easyreport.engine.util;

public class ComparatorUtils {

    /**
     * 按数字大小优先的字符串比较方法
     *
     * @param x 字符串1
     * @param y 字符串2
     * @return x == y 为0，x>y为1，x<y为-1
     */
    public static int compareByDigitPriority(String x, String y) {
        char[] xChars = x.toCharArray();
        char[] yChars = y.toCharArray();

        int i = 0, j = 0;
        while (i < xChars.length && j < yChars.length) {
            if (Character.isDigit(xChars[i]) && Character.isDigit(yChars[j])) {
                StringBuilder s1 = new StringBuilder(""), s2 = new StringBuilder("");
                while (i < xChars.length && Character.isDigit(xChars[i])) {
                    s1.append(xChars[i]);
                    i++;
                }
                while (j < yChars.length && Character.isDigit(yChars[j])) {
                    s2.append(yChars[j]);
                    j++;
                }
                if (Integer.parseInt(s1.toString()) > Integer.parseInt(s2.toString()))
                    return 1;
                if (Integer.parseInt(s1.toString()) < Integer.parseInt(s2.toString()))
                    return -1;
            } else {
                if (xChars[i] > yChars[j])
                    return 1;
                if (xChars[i] < yChars[j])
                    return -1;
                i++;
                j++;
            }
        }

        if (xChars.length == yChars.length)
            return 0;
        return xChars.length > yChars.length ? 1 : -1;
    }
}
