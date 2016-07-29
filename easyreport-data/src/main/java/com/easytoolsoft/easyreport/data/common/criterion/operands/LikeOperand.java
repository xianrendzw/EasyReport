package com.easytoolsoft.easyreport.data.common.criterion.operands;

public class LikeOperand extends AbstractOperand {

    private final String columnName;
    private final Object columnValue;

    public LikeOperand(String columnName, Object columnValue) {
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    @Override
    protected String toExpression() {
        return String.format("%1$s like '%2$s' ", this.columnName, this.columnValue);
    }
}
