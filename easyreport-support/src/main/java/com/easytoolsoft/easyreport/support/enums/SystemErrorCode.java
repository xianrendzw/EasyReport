package com.easytoolsoft.easyreport.support.enums;

import com.easytoolsoft.easyreport.support.i18n.LocaleUtils;

/**
 * @author Tom Deng
 * @date 2017-05-26
 **/
public enum SystemErrorCode implements ErrorCode {
    UNKNOWN_ERROR(-1, "error.code.sys.unknow"),
    HTTP_MESSAGE_NOT_READABLE(900, "error.code.sys.900"),
    DATA_VALIDATION_FAILURE(901, "error.code.sys.901"),
    DATA_BIND_VALIDATION_FAILURE(902, "error.code.sys.902"),
    SQL_EXECUTE_FAILURE(903, "error.code.sys.903"),
    METHOD_ARGUMENT_NOT_VALID(904, "error.code.sys.902"),

    BAD_REQUEST(400, "error.code.httpstatus.400"),
    UNAUTHORIZED(401, "error.code.httpstatus.401"),
    FORBIDDEN(403, "error.code.httpstatus.403"),
    NOT_FOUND(404, "error.code.httpstatus.404"),
    METHOD_NOT_ALLOWED(405, "error.code.httpstatus.405"),
    UNSUPPORTED_MEDIA_TYPE(415, "error.code.httpstatus.415"),
    INTERNAL_SERVER_ERROR(500, "error.code.httpstatus.500");

    private int code;
    private String message;

    SystemErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage(this.message);
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }

    /**
     * @param code
     * @return
     */
    public static SystemErrorCode valueOf(int code) {
        for (SystemErrorCode errorCode : values()) {
            if (errorCode.code == code) {
                return errorCode;
            }
        }
        return SystemErrorCode.UNKNOWN_ERROR;
    }
}
