package com.easyreport.data.criterion.operands;

public class NotInOperand extends AbstractOperand {

	private String columnName;
	private Object columnValue;

	public NotInOperand(String columnName, Object columnValue) {
		this.columnName = columnName;
		this.columnValue = columnValue;
	}

	@Override
	protected String toExpression() {
		return String.format("%1$s NOT IN (%2$s) ", this.columnName, this.columnValue);
	}
}
