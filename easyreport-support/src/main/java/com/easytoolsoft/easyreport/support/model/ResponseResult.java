package com.easytoolsoft.easyreport.support.model;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

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

    public static <U> ResponseResult success(final U data) {
        return build(0, "", "", data);
    }

    public static ResponseResult failure(final int code, final String msg) {
        return failure(code, msg, "");
    }

    public static ResponseResult failure(final String msg, final String detailMsg) {
        return failure(-1, msg, detailMsg);
    }

    public static ResponseResult failure(final int code, final String msg, final String detailMsg) {
        return build(code, msg, detailMsg, null);
    }

    public static <U> ResponseResult build(final int code, final String msg, final String detailMsg, final U data) {
        return new ResponseResult<>(code, msg, detailMsg, data);
    }
}
