package com.easytoolsoft.easyreport.data.common.criterion.operands;

public class OrConjOperand extends AbstractOperand {

    public OrConjOperand() {
    }

    @Override
    protected String toExpression() {
        return " OR ";
    }
}
