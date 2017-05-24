package com.easytoolsoft.easyreport.engine.exception;

/**
 * 报表查询参数异常
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public class QueryParamsException extends RuntimeException {
    private static final long serialVersionUID = -8464685300165030321L;

    public QueryParamsException() {
        super();
    }

    public QueryParamsException(final String message) {
        super(message);
    }

    public QueryParamsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public QueryParamsException(final Throwable cause) {
        super("报表查询参数设置有错误,可能未设置SQL语句或SQL语句不正确，或不正确的参数设置!", cause);
    }
}
