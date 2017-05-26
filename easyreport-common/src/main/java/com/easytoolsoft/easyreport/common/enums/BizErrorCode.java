package com.easytoolsoft.easyreport.common.enums;

import com.easytoolsoft.easyreport.support.enums.ErrorCode;
import com.easytoolsoft.easyreport.support.i18n.LocaleUtils;

/**
 * @author Tom Deng
 * @date 2017-05-01
 **/
public enum BizErrorCode implements ErrorCode {
    BAB_CREDENTIALS(11000, "error.code.biz.11000"),
    REFRESH_TOKEN_FAILURE(11001, "error.code.biz.11001");

    private int code;
    private String message;

    BizErrorCode(int code, String message) {
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
}
