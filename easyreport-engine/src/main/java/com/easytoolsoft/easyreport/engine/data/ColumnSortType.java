package com.easytoolsoft.easyreport.engine.data;

/**
 * 报表列排序类型
 */
public enum ColumnSortType {

    /**
     * 默认
     */
    DEFAULT(0),

    /**
     * 数字优先升序
     */
    DIGIT_ASCENDING(1),

    /**
     * 数字优先降序
     */
    DIGIT_DESCENDING(2),

    /**
     * 字符优先升序
     */
    CHAR_ASCENDING(3),

    /**
     * 字符优先降序
     */
    CHAR_DESCENDING(4);

    private final int value;

    ColumnSortType(int value) {
        this.value = value;
    }

    public static ColumnSortType valueOf(int arg) {
        if (arg == 0)
            return DEFAULT;
        if (arg == 1)
            return DIGIT_ASCENDING;
        if (arg == 2)
            return DIGIT_DESCENDING;
        if (arg == 3)
            return CHAR_ASCENDING;
        if (arg == 4)
            return CHAR_DESCENDING;
        return DEFAULT;
    }

    public int getValue() {
        return value;
    }
}
