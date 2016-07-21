package com.easytoolsoft.easyreport.data.criterion.operands;

public class LikeOperand extends AbstractOperand {

    private String columnName;
    private Object columnValue;

    public LikeOperand(String columnName, Object columnValue) {
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    @Override
    protected String toExpression() {
        return String.format("%1$s like '%2$s' ", this.columnName, this.columnValue);
    }
}
