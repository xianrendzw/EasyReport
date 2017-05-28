package com.easytoolsoft.easyreport.support.model;

import java.io.Serializable;

import com.easytoolsoft.easyreport.support.consts.AppEnvConsts;
import com.easytoolsoft.easyreport.support.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * ResponseBody注解返回的JSON对象类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseResult<T> implements Serializable {
    /**
     * 0表示成功，>0表示失败,<0系统保留
     */
    private int code = 0;

    /**
     * 提示信息
     */
    private String msg = "";

    /**
     * 详细提示信息
     */
    private String detailMsg = "";

    /**
     * 返回数据
     */
    private T data;

    /**
     * @param data
     * @param <U>
     * @return
     */
    public static <U> ResponseResult success(final U data) {
        return build(0, "", "", data);
    }

    /**
     * @param code
     * @param msg
     * @return
     */
    public static ResponseResult failure(final int code, final String msg) {
        return failure(code, msg, "");
    }

    /**
     * @param errorCode
     * @return
     */
    public static ResponseResult failure(final ErrorCode errorCode) {
        return failure(errorCode, "");
    }

    /**
     * @param errorCode
     * @param ex
     * @return
     */
    public static ResponseResult failure(final ErrorCode errorCode, final Throwable ex) {
        return failure(errorCode, AppEnvConsts.isProductionMode() ? "" : ExceptionUtils.getStackTrace(ex));
    }

    /**
     * @param errorCode
     * @param detailMsg
     * @return
     */
    public static ResponseResult failure(final ErrorCode errorCode, final String detailMsg) {
        return failure(errorCode.getCode(), errorCode.getMessage(), detailMsg);
    }

    /**
     * @param errorCode
     * @param data
     * @param <U>
     * @return
     */
    public static <U> ResponseResult failure(final ErrorCode errorCode, final U data) {
        return build(errorCode.getCode(), errorCode.getMessage(), "", data);
    }

    /**
     * @param msg
     * @return
     */
    public static ResponseResult failure(final String msg) {
        return failure(-1, msg, "");
    }

    /**
     * @param msg
     * @param detailMsg
     * @return
     */
    public static ResponseResult failure(final String msg, final String detailMsg) {
        return failure(-1, msg, detailMsg);
    }

    /**
     * @param code
     * @param msg
     * @param detailMsg
     * @return
     */
    public static ResponseResult failure(final int code, final String msg, final String detailMsg) {
        return build(code, msg, detailMsg, null);
    }

    /**
     * @param code
     * @param msg
     * @param ex
     * @return
     */
    public static ResponseResult failure(final int code, final String msg, final Throwable ex) {
        return build(code, msg, AppEnvConsts.isProductionMode() ? "" : ExceptionUtils.getStackTrace(ex), null);
    }

    /**
     * @param code
     * @param msg
     * @param detailMsg
     * @param data
     * @param <U>
     * @return
     */
    public static <U> ResponseResult build(final int code, final String msg, final String detailMsg, final U data) {
        return new ResponseResult<>(code, msg, detailMsg, data);
    }
}