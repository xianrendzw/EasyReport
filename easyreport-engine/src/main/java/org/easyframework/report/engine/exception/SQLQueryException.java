package org.easyframework.report.engine.exception;

/**
 * 报表SQL查询语句执行失败异常
 *
 */
public class SQLQueryException extends RuntimeException {
	private static final long serialVersionUID = -8642401582122059411L;

	public SQLQueryException() {
		super();
	}

	public SQLQueryException(String message) {
		super(message);
	}

	public SQLQueryException(String message, Throwable cause) {
		super(message, cause);
	}

	public SQLQueryException(Throwable cause) {
		super("报表SQL查询语句执行失败!可能有以下原因：</br>1.报表配置列与SQL语句中的列不一致;</br>2.SQL语句语法错误;</br>3.连接数据库服务器失败或超时;", cause);
	}
}
