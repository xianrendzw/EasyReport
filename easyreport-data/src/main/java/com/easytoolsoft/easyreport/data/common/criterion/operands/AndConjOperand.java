package com.easytoolsoft.easyreport.data.common.criterion.operands;

public class AndConjOperand extends AbstractOperand {

    public AndConjOperand() {
    }

    @Override
    protected String toExpression() {
        return " AND ";
    }
}
