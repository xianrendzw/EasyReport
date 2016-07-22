package com.easytoolsoft.easyreport.data.criterion.operands;

public enum Bracket {
    Left("("), Right(")");

    private String abbreviation;

    Bracket(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }
}
