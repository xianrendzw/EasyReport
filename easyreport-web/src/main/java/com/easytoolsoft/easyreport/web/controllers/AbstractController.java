package com.easytoolsoft.easyreport.web.controllers;

import com.easytoolsoft.easyreport.common.viewmodel.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void setSuccessResult(JsonResult result, String msg) {
        result.setSuccess(true);
        result.setMsg(StringUtils.isBlank(msg) ? "操作成功！" : msg);
    }

    protected void setFailureResult(JsonResult result, String msg) {
        result.setSuccess(false);
        result.setMsg(StringUtils.isBlank(msg) ? "操作失败！" : msg);
    }

    protected void setExceptionResult(JsonResult result, Exception ex) {
        result.setSuccess(false);
        result.setMsg(String.format("系统异常, 原因:%s", ex.getMessage()));
        this.logException(ex);
    }

    protected void logException(Exception ex) {
        this.logException("系统异常", ex);
    }

    protected void logException(String msg, Exception ex) {
        logger.error(msg, ex);
    }
}
