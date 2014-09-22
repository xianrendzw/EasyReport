package org.easyframework.report.data.exceptions;

public class SQLConditionException extends RuntimeException {
	private static final long serialVersionUID = -1085259399790850343L;

	public SQLConditionException() {
		super("SQL语句条件表达式格式错误.");
	}

	public SQLConditionException(String message) {
		super(message);
	}

	public SQLConditionException(String message, Throwable cause) {
		super(message, cause);
	}

	public SQLConditionException(Throwable cause) {
		super(cause);
	}
}
