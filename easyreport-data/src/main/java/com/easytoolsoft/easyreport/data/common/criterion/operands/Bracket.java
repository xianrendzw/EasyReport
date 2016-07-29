package com.easytoolsoft.easyreport.data.common.criterion.operands;

public enum Bracket {
    Left("("), Right(")");

    private final String abbreviation;

    Bracket(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }
}
