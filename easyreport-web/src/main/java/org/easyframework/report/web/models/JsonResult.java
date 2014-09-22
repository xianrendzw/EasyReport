package org.easyframework.report.web.models;

import java.io.Serializable;

/**
 * JSON返回结果类
 *
 */
public class JsonResult implements Serializable {
	private static final long serialVersionUID = -2107307930167659752L;
	protected boolean isSuccess;
	protected String msg;

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
