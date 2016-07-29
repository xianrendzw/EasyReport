package com.easytoolsoft.easyreport.data.common.criterion.operands;

public class BetweenOperand extends AbstractOperand {

    private final String columnName;
    private final Object lowerValue;
    private final Object higherValue;

    public BetweenOperand(String columnName, Object lowerValue, Object higherValue) {
        this.columnName = columnName;
        this.lowerValue = lowerValue;
        this.higherValue = higherValue;
    }

    @Override
    protected String toExpression() {
        return String.format("%1$s BETWEEN %2$s AND %3$s ", this.columnName, this.lowerValue, this.higherValue);
    }
}
