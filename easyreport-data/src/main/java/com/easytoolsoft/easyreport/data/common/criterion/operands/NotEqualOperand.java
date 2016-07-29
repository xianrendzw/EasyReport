package com.easytoolsoft.easyreport.data.common.criterion.operands;

public class NotEqualOperand extends AbstractOperand {

    private final String columnName;
    private final Object columnValue;

    public NotEqualOperand(String columnName, Object columnValue) {
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    @Override
    protected String toExpression() {
        return String.format("%1$s <> %2$s ", this.columnName, this.columnValue);
    }
}
