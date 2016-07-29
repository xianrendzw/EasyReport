package com.easytoolsoft.easyreport.data.common.criterion.operands;

public class BracketOperand extends AbstractOperand {

    private final Bracket bracket;

    public BracketOperand(Bracket bracket) {
        this.bracket = bracket;
    }

    @Override
    protected String toExpression() {
        switch (this.bracket) {
            case Left:
                return "(";
            case Right:
                return ")";
            default:
                return "";
        }
    }
}
