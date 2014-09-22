package org.easyframework.report.web.controllers;

import org.easyframework.report.web.models.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected void setErrorResult(JsonResult result, Exception ex) {
		result.setSuccess(false);
		result.setMsg(String.format("%s 原因:%s", "操作失败！", ex.getMessage()));
		logger.error(ex.toString());
	}
}
