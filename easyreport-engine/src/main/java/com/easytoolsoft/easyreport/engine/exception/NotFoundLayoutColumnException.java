package com.easytoolsoft.easyreport.engine.exception;

/**
 * 报表维度列中没有找到布局维度列异常类
 *
 * @author tomdeng
 */
public class NotFoundLayoutColumnException extends RuntimeException {
    private static final long serialVersionUID = 9070983743782537147L;

    public NotFoundLayoutColumnException() {
        super("没找到报表中的布局列，请配置布局列!");
    }

    public NotFoundLayoutColumnException(final String message) {
        super(message);
    }

    public NotFoundLayoutColumnException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotFoundLayoutColumnException(final Throwable cause) {
        super(cause);
    }
}
