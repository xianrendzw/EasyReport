package com.easytoolsoft.easyreport.engine.data;

import com.easytoolsoft.easyreport.engine.util.NumberFormatUtils;

public class ReportDataCell {
    private final ReportDataColumn column;
    private final String name;
    private Object value;

    public ReportDataCell(ReportDataColumn column, String name, Object value) {
        this.column = column;
        this.name = name;
        this.value = value;
    }

    public ReportDataColumn getColumn() {
        return this.column;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        int decimals = this.column.getMetaData().getDecimals();
        if (this.column.getMetaData().isPercent()) {
            decimals = decimals <= 0 ? 2 : decimals;
            return NumberFormatUtils.percentFormat(this.value, decimals);
        }
        if (this.column.getMetaData().getDataType().equals("DECIMAL")
                || this.column.getMetaData().getDataType().equals("DOUBLE")
                || this.column.getMetaData().getDataType().equals("FLOAT")) {
            decimals = decimals <= 0 ? 4 : decimals;
            return NumberFormatUtils.decimalFormat(value, decimals);
        }
        return NumberFormatUtils.format(this.value);
    }
}
