package com.easytoolsoft.easyreport.common.viewmodel;

import java.io.Serializable;

/**
 * json 结果返回对象
 */
public class JsonResult implements Serializable {
    private static final long serialVersionUID = 3142582884351240086L;
    private boolean isSuccess;
    private String msg;

    public JsonResult(boolean isSuccess, String msg) {
        this.isSuccess = isSuccess;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
