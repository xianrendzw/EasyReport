package com.easytoolsoft.easyreport.web.viewmodel;

import lombok.Data;

import java.io.Serializable;

/**
 * json 结果返回对象
 */
@Data
@SuppressWarnings("serial")
public class JsonResult<T> implements Serializable {
    private boolean success;
    private String msg;
    private T data;

    public JsonResult() {
        this(true, "");
    }

    public JsonResult(T data) {
        this(true, "");
        this.data = data;
    }

    public JsonResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}
