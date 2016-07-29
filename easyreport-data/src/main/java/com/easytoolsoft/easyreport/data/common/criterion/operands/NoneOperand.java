package com.easytoolsoft.easyreport.data.common.criterion.operands;

public class NoneOperand extends AbstractOperand {

    public NoneOperand() {
    }

    @Override
    protected String toExpression() {
        return "";
    }
}
