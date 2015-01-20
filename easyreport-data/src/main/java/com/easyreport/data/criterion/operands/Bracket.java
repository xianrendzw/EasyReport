package com.easyreport.data.criterion.operands;

public enum Bracket {
	Left("("), Rgiht(")");

	private Bracket(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}

	private String abbreviation;
}
