package com.easytoolsoft.easyreport.engine.exception;

/**
 * 模板解析异常
 *
 * @author tomdeng
 */
public class TemplatePraseException extends RuntimeException {
    private static final long serialVersionUID = -1347415208165095730L;

    public TemplatePraseException() {
        super();
    }

    public TemplatePraseException(final String message) {
        super(message);
    }

    public TemplatePraseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TemplatePraseException(final Throwable cause) {
        super("模板引擎解析出错.详细信息：" + cause.getMessage(), cause);
    }
}
