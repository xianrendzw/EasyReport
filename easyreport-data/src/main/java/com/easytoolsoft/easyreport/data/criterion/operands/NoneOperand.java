package com.easytoolsoft.easyreport.data.criterion.operands;

public class NoneOperand extends AbstractOperand {

    public NoneOperand() {
    }

    @Override
    protected String toExpression() {
        return "";
    }
}
