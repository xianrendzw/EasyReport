package com.easytoolsoft.easyreport.common.viewmodel;

import java.io.Serializable;

/**
 * 参数化类型的JSON返回结果类
 *
 * @param <T> 参数化对象
 */
public class ParamJsonResult<T> extends JsonResult implements Serializable {
    private static final long serialVersionUID = 3142582884351240086L;
    private T data;

    public ParamJsonResult(boolean isSuccess, String msg) {
        super(isSuccess, msg);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
