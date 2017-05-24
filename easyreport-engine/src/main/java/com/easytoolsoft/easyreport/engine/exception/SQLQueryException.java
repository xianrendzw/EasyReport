package com.easytoolsoft.easyreport.engine.exception;

/**
 * 报表SQL查询语句执行失败异常
 *
 * @author tomdeng
 */
public class SQLQueryException extends RuntimeException {
    private static final long serialVersionUID = -8642401582122059411L;

    public SQLQueryException() {
        super();
    }

    public SQLQueryException(final String message) {
        super(message);
    }

    public SQLQueryException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SQLQueryException(final Throwable cause) {
        super("报表SQL查询语句执行失败!请查看报表配置列与SQL语句中的列是否一致.详细信息：" + cause.getMessage(), cause);
    }
}
